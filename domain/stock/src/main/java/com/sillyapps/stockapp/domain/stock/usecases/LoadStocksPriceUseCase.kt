package com.sillyapps.stockapp.domain.stock.usecases

import com.sillyapps.stockapp.domain.stock.StockRepository
import javax.inject.Inject

class LoadStocksPriceUseCase @Inject constructor(
  private val repository: StockRepository
) {

  suspend operator fun invoke(stockSymbols: List<String>) {
    repository.loadStockPrices(stockSymbols)
  }

}