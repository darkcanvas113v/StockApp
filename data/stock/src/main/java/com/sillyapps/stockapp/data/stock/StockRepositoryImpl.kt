package com.sillyapps.stockapp.data.stock

import com.sillyapps.core_di.modules.IOCoroutineScope
import com.sillyapps.core_di.modules.IODispatcher
import com.sillyapps.core_network.Resource
import com.sillyapps.core_util.BiDirectionalMap
import com.sillyapps.stockapp.data.stock.dto.ServerMessageDto
import com.sillyapps.stockapp.data.stock.dto.ServerRequestDto
import com.sillyapps.stockapp.data.stock.dto.StockTradeDto
import com.sillyapps.stockapp.data.stock.dto.toDomainModel
import com.sillyapps.stockapp.domain.stock.model.Stock
import com.sillyapps.stockapp.domain.stock.StockRepository
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
  @IOCoroutineScope private val ioScope: CoroutineScope,
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,

  private val finnhubApi: FinnhubApi,
  private val httpClient: OkHttpClient
): StockRepository {

  private var lastFrom: Int = 0
  private var lastTo: Int = 0
  private val stockPosToSymbolMap = BiDirectionalMap<Int, String>()

  private val mStocksList: MutableList<Stock> = mutableListOf()
  private val mStocksResource: MutableStateFlow<Resource<List<Stock>>> = MutableStateFlow(Resource.Loading())

  private val messageAdapter = Moshi.Builder().build().adapter(ServerMessageDto::class.java)
  private val requestAdapter = Moshi.Builder().build().adapter(ServerRequestDto::class.java)

  private val webSocket = buildWebSocket()

  private fun buildWebSocket(): WebSocket {
    val request = Request.Builder()
      .url("wss://ws.finnhub.io/")
      .build()

    return httpClient.newWebSocket(
      request, object : WebSocketListener() {

        override fun onMessage(webSocket: WebSocket, text: String) {
          Timber.e("Received message from server:\n $text")
          val message = messageAdapter.fromJson(text) ?: return
          if (message.type == "trade") {
            val stockPrice = message.data?.last() ?: return
            val index = stockPosToSymbolMap.getByValue(stockPrice.symbol) ?: return
            mStocksList[index] = mStocksList[index].copy(price = stockPrice.lastPrice)

            ioScope.launch(ioDispatcher) { mStocksResource.emit(Resource.Success(data = mStocksList)) }
          }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
          Timber.e(t.localizedMessage)
        }
      }
    )
  }

  override fun getStocks(): Flow<Resource<List<Stock>>> {
    ioScope.launch(ioDispatcher) {
      try {
        val stocks = finnhubApi.getStocks().map { it.toDomainModel() }
        mStocksList.addAll(stocks)

        stocks.forEachIndexed { index, stock ->
          stockPosToSymbolMap.put(index, stock.symbol)
        }

        mStocksResource.value = Resource.Success(mStocksList)
        loadStockPrices(0, 10)
      } catch (e: HttpException) {
        mStocksResource.value = (Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
      } catch (e: IOException) {
        mStocksResource.value = (Resource.Error("Couldn't reach server. Check your internet connection."))
      }
    }

    return mStocksResource
  }

  override suspend fun loadStockPrices(fromIndex: Int, toIndex: Int) = withContext(ioDispatcher) {
    for (i in lastFrom until lastTo) {
      val request = requestAdapter.toJson(ServerRequestDto("unsubscribe", mStocksList[i].symbol))
      Timber.e("Requesting: $request")
      webSocket.send(request)
    }

    for (i in fromIndex until toIndex) {
      val value = mStocksList[i]

      if (value.price == null) {
        val price = finnhubApi.getStockQuoteBySymbol(value.symbol)
        mStocksList[i] = value.copy(price = price.currentPrice)
        mStocksResource.emit(
          Resource.Success(
            data = mStocksList
          )
        )
      }

      val request = requestAdapter.toJson(ServerRequestDto("subscribe", value.symbol))
      Timber.e("Requesting: $request")
      webSocket.send(request)
    }
  }

}