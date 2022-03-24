package com.sillyapps.stockapp.domain.stock.usecases

import com.sillyapps.stockapp.domain.stock.StockRepository
import com.sillyapps.stockapp.domain.stock.model.StockEvent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetStockEventsUseCase @Inject constructor(
  private val repository: StockRepository
) {
  operator fun invoke(): Flow<StockEvent> = repository.getEventBus()
}