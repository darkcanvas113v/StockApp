package com.sillyapps.stockapp.data.stock

import com.sillyapps.core_di.modules.IOCoroutineScope
import com.sillyapps.core_di.modules.IODispatcher
import com.sillyapps.core_network.tryToLoad
import com.sillyapps.core_util.BiDirectionalMap
import com.sillyapps.core_util.modify
import com.sillyapps.stockapp.data.stock.models.toDomainModel
import com.sillyapps.stockapp.data.stock.models.trades_websocket.ServerMessageDto
import com.sillyapps.stockapp.data.stock.models.trades_websocket.ServerRequestDto
import com.sillyapps.stockapp.domain.stock.model.Company
import com.sillyapps.stockapp.domain.stock.model.Quote
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
              val value = list[index]
              mStocks.value = list.modify(
                  pos = index,
                  value = value.copy(
                    quote = value.quote?.copy(
                      currentPrice = stockPrice.lastPrice
                    )))
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

      val stockPos = stockPosToSymbolMap.getByValue(stockSymbol) ?: continue
      var value = mStocks.value[stockPos]
      var stockModified = false

      if (value.company == null) {
        val company = getCompanyInfo(stockSymbol)
        value = value.copy(company = company)
        stockModified = true
      }

      if (value.quote == null) {
        val quote = getStockPrice(stockSymbol)
        value = value.copy(quote = quote)
        stockModified = true
      }

      if (stockModified)
        mStocks.value = mStocks.value.modify(stockPos, value)

      val request = requestAdapter.toJson(ServerRequestDto(ServerRequestDto.TYPE_SUBSCRIBE, stockSymbol))
      Timber.e("Requesting: $request")
      webSocket.send(request)
    }

    lastLoadedStocks = stockSymbols
  }

  override fun disconnect() {
    webSocket.cancel()
  }

  private suspend fun getCompanyInfo(symbol: String): Company? {
    return tryToLoad(
      tryBlock = {
        finnhubApi.getCompanyBySymbol(symbol).toDomainModel()
      },
      onHttpException = {
        Timber.e("HttpException while trying to load company for symbol: $symbol")
      },
      onIOException = {
        Timber.e("IOException while trying to load company for symbol: $symbol")
      }
    )
  }

  private suspend fun getStockPrice(symbol: String): Quote? {
    return tryToLoad(
      tryBlock = {
        finnhubApi.getStockQuoteBySymbol(symbol).toDomainModel()
      },
      onHttpException = {
        Timber.e("HttpException while trying to load quote for symbol: $symbol")
      },
      onIOException = {
        Timber.e("IOException while trying to load quote for symbol: $symbol")
      }
    )
  }

}