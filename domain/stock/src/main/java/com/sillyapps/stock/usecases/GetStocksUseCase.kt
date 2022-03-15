package com.sillyapps.stock.usecases

import com.sillyapps.network.Resource
import com.sillyapps.stock.StockRepository
import com.sillyapps.stock.model.Stock
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStocksUseCase @Inject constructor(
  private val repository: StockRepository
) {

  operator fun invoke(): Flow<Resource<List<Stock>>> = repository.getStocks()

}