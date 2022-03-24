package com.sillyapps.stockapp.domain.stock.usecases

import com.sillyapps.core_network.Resource
import com.sillyapps.stockapp.domain.stock.StockRepository
import com.sillyapps.stockapp.domain.stock.model.Stock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetStocksUseCase @Inject constructor(
  private val repository: StockRepository
) {

  operator fun invoke(): Flow<Resource<List<Stock>>> =
    repository.getStocks().map {
      sorted(it)
    }

  private fun sorted(resource: Resource<List<Stock>>): Resource<List<Stock>> {
    return if (resource is Resource.Success)
      Resource.Success(resource.data?.sortedBy { it.name } ?: emptyList())
    else
      resource
  }

}