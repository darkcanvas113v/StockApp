package com.sillyapps.stockapp.data.stock

import com.sillyapps.core_di.modules.IOCoroutineScope
import com.sillyapps.core_di.modules.IODispatcher
import com.sillyapps.core_util.BiDirectionalMap
import com.sillyapps.core_util.modify
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
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class StockDataSourceImpl @Inject constructor(
  @IOCoroutineScope private val ioScope: CoroutineScope,
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,

  private val httpClient: OkHttpClient,
  private val finnhubApi: FinnhubApi
): StockDataSource {

  private var lastLoadedStocks: List<String> = emptyList()
  private val stockPosToSymbolMap = BiDirectionalMap<Int, String>()

  private val mStocks: MutableStateFlow<List<Stock>> = MutableStateFlow(emptyList())

  private val messageAdapter = Moshi.Builder().build().adapter(ServerMessageDto::class.java)
  private val requestAdapter = Moshi.Builder().build().adapter(ServerRequestDto::class.java)

  private val webSocket: WebSocket by lazy {
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
  }

  override fun getStocks(): Flow<List<Stock>> {
    return mStocks
  }

  override suspend fun loadStockPrices(stockSymbols: List<String>) = withContext(ioDispatcher) {

    for (symbol in lastLoadedStocks) {
      if (stockSymbols.contains(symbol)) continue

      val request = requestAdapter.toJson(ServerRequestDto(ServerRequestDto.TYPE_UNSUBSCRIBE, symbol))
      Timber.e("Requesting: $request")
      webSocket.send(request)
    }

    for (stockSymbol in stockSymbols) {
      if (lastLoadedStocks.contains(stockSymbol)) continue

      val stockPos = stockPosToSymbolMap.getByValue(stockSymbol)!!
      val value = mStocks.value[stockPos]

      val newStock = getStockPrice(value)
      mStocks.value = mStocks.value.modify(stockPos, newStock)

      val request = requestAdapter.toJson(ServerRequestDto(ServerRequestDto.TYPE_SUBSCRIBE, stockSymbol))
      Timber.e("Requesting: $request")
      webSocket.send(request)
    }

    lastLoadedStocks = stockSymbols
  }

  private suspend fun getStockPrice(stock: Stock): Stock {
    try {
      val stockQuote = finnhubApi.getStockQuoteBySymbol(stock.symbol)
      return stock.copy(
        price = stockQuote.currentPrice,
        percentChange = stockQuote.percentChange
      )
    } catch (e: HttpException) {
      Timber.e(e.localizedMessage ?: "An unexpected error occured")
    } catch (e: IOException) {
      Timber.e("Couldn't reach server. Check your internet connection.")
    }
    return stock
  }

}