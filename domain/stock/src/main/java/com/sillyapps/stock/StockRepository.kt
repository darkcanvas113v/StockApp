package com.sillyapps.stock

import com.sillyapps.network.Resource
import com.sillyapps.stock.model.Stock
import kotlinx.coroutines.flow.Flow

interface StockRepository {

  fun getStocks(): Flow<Resource<List<Stock>>>

}