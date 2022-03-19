package com.sillyapps.stockapp.data.stock

import com.sillyapps.core_di.modules.IOCoroutineScope
import com.sillyapps.core_di.modules.IODispatcher
import com.sillyapps.core_network.Resource
import com.sillyapps.core_util.BiDirectionalMap
import com.sillyapps.core_util.modify
import com.sillyapps.stockapp.data.stock.di.WebSocketClient
import com.sillyapps.stockapp.data.stock.models.ServerMessageDto
import com.sillyapps.stockapp.data.stock.models.ServerRequestDto
import com.sillyapps.stockapp.domain.stock.model.Stock
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import timber.log.Timber
import javax.inject.Inject

class StockDataSourceImpl @Inject constructor(
  @WebSocketClient private val httpClient: OkHttpClient,
  @IOCoroutineScope private val ioScope: CoroutineScope,
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,
  private val finnhubApi: FinnhubApi
): StockDataSource {

  private var lastFrom: Int = 0
  private var lastTo: Int = 0
  private val stockPosToSymbolMap = BiDirectionalMap<Int, String>()

  private val mStocks: MutableStateFlow<List<Stock>> = MutableStateFlow(emptyList())

  private val messageAdapter = Moshi.Builder().build().adapter(ServerMessageDto::class.java)
  private val requestAdapter = Moshi.Builder().build().adapter(ServerRequestDto::class.java)

  private val webSocket: WebSocket = kotlin.run {
    val request = Request.Builder()
      .url("wss://ws.finnhub.io/")
      .build()

    httpClient.newWebSocket(
      request, object : WebSocketListener() {

        override fun onMessage(webSocket: WebSocket, text: String) {
          Timber.e("Received message from server:\n $text")
          val message = messageAdapter.fromJson(text) ?: return
          if (message.type == "trade") {
            val stockPrice = message.data?.last() ?: return
            val index = stockPosToSymbolMap.getByValue(stockPrice.symbol) ?: return

            val list = (mStocks.value)

            ioScope.launch(ioDispatcher) {
              mStocks.value = list.modify(
                  pos = index,
                  value = list[index].copy(price = stockPrice.lastPrice))
            }
          }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
          Timber.e(t.localizedMessage)
        }
      }
    )
  }

  override suspend fun setInitialStocks(stocks: List<Stock>) {
    stocks.forEachIndexed { index, stock ->
      stockPosToSymbolMap.put(index, stock.symbol)
    }

    mStocks.value = stocks
    loadStockPrices(0, 10)
  }

  override fun getStocks(): Flow<List<Stock>> {
    return mStocks
  }

  override suspend fun loadStockPrices(fromIndex: Int, toIndex: Int) = withContext(ioDispatcher) {

    for (i in lastFrom until lastTo) {
      val request = requestAdapter.toJson(ServerRequestDto(ServerRequestDto.TYPE_UNSUBSCRIBE, mStocks.value[i].symbol))
      Timber.e("Requesting: $request")
      webSocket.send(request)
    }

    for (i in fromIndex until toIndex) {
      val value = mStocks.value[i]

      if (value.price == null) {
        val price = finnhubApi.getStockQuoteBySymbol(value.symbol)

        mStocks.value = mStocks.value.modify(i, value.copy(price = price.currentPrice))
      }

      val request = requestAdapter.toJson(ServerRequestDto(ServerRequestDto.TYPE_SUBSCRIBE, value.symbol))
      Timber.e("Requesting: $request")
      webSocket.send(request)
    }
    lastFrom = fromIndex
    lastTo = toIndex
  }

}