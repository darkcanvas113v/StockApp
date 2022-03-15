package com.sillyapps.stock

import com.sillyapps.stock.dto.StockSymbolDto
import retrofit2.http.GET

interface FinnhubApi {

  @GET("/stock/symbol?exchange=US")
  suspend fun getStocks(): List<StockSymbolDto>

//  @GET("/quote?symbol={stockId}")
//  suspend fun getStockById(@Path("stockId") stockId: String): StockQuoteDto

}