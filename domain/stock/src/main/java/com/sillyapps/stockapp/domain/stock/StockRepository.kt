package com.sillyapps.stockapp.domain.stock

import com.sillyapps.core_network.Resource
import com.sillyapps.stockapp.domain.stock.model.Stock
import com.sillyapps.stockapp.domain.stock.model.StockEvent
import kotlinx.coroutines.flow.Flow

interface StockRepository {

  fun getStocks(): Flow<Resource<List<Stock>>>

  suspend fun loadStockPrices(stockSymbols: List<String>)

  fun disconnect()

  fun getEventBus(): Flow<StockEvent>

}