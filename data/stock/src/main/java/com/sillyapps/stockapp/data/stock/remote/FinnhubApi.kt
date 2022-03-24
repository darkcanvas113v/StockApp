package com.sillyapps.stockapp.data.stock.remote

import com.sillyapps.stockapp.data.stock.remote.models.CompanyDto
import com.sillyapps.stockapp.data.stock.remote.models.QuoteDto
import com.sillyapps.stockapp.data.stock.remote.models.StockSymbolDto
import retrofit2.http.GET
import retrofit2.http.Query

interface FinnhubApi {

  @GET("/api/v1/stock/symbol?exchange=US")
  suspend fun getStocks(): List<StockSymbolDto>

  @GET("/api/v1/quote")
  suspend fun getStockQuoteBySymbol(@Query("symbol") symbol: String): QuoteDto

  @GET("/api/v1/stock/profile2")
  suspend fun getCompanyBySymbol(@Query("symbol") symbol: String): CompanyDto

}