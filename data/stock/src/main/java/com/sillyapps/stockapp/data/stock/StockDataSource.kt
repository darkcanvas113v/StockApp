package com.sillyapps.stockapp.data.stock

import com.sillyapps.stockapp.domain.stock.model.Stock
import kotlinx.coroutines.flow.Flow

interface StockDataSource {

  suspend fun setInitialStocks(stocks: List<Stock>)

  fun getStocks(): Flow<List<Stock>>

  suspend fun loadStockPrices(fromIndex: Int, toIndex: Int)

}