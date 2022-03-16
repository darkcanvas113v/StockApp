package com.sillyapps.stockapp.data.stock

import com.sillyapps.stockapp.data.stock.dto.QuoteDto
import com.sillyapps.stockapp.data.stock.dto.StockSymbolDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FinnhubApi {

  @GET("/api/v1/stock/symbol?exchange=US")
  suspend fun getStocks(): List<StockSymbolDto>

  @GET("/api/v1/quote")
  suspend fun getStockQuoteBySymbol(@Query("symbol") symbol: String): QuoteDto

}