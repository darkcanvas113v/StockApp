package com.sillyapps.stockapp.data.stock

import com.sillyapps.stockapp.data.stock.dto.StockSymbolDto
import retrofit2.http.GET

interface FinnhubApi {

  @GET("/api/v1/stock/symbol?exchange=US")
  suspend fun getStocks(): List<StockSymbolDto>

//  @GET("/quote?symbol={stockId}")
//  suspend fun getStockById(@Path("stockId") stockId: String): StockQuoteDto

}