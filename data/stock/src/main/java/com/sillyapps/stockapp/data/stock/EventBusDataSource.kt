package com.sillyapps.stockapp.data.stock

import com.sillyapps.core_di.AppScope
import com.sillyapps.stockapp.domain.stock.model.StockEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import timber.log.Timber
import javax.inject.Inject

@AppScope
class EventBusDataSource @Inject constructor() {

  private val eventBus = MutableSharedFlow<StockEvent>(
    extraBufferCapacity = 1
  )

  fun get(): Flow<StockEvent> = eventBus

  fun onEvent(event: StockEvent) {
    eventBus.tryEmit(event)
  }

}