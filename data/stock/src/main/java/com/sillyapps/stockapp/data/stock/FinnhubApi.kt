package com.sillyapps.stockapp.data.stock

import com.sillyapps.stockapp.data.stock.models.QuoteDto
import com.sillyapps.stockapp.data.stock.models.StockSymbolDto
import retrofit2.http.GET
import retrofit2.http.Query

interface FinnhubApi {

  @GET("/api/v1/stock/symbol?exchange=US")
  suspend fun getStocks(): List<StockSymbolDto>

  @GET("/api/v1/quote")
  suspend fun getStockQuoteBySymbol(@Query("symbol") symbol: String): QuoteDto

}