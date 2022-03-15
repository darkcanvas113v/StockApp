package com.sillyapps.stock.usecases

import com.sillyapps.stock.StockRepository
import javax.inject.Inject

class LoadStocksPriceUseCase @Inject constructor(
  private val repository: StockRepository
) {



}