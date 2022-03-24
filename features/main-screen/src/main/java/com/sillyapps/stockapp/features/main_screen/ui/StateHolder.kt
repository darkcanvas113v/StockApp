package com.sillyapps.stockapp.features.main_screen.ui

import androidx.compose.runtime.State
import com.sillyapps.stockapp.domain.stock.model.Stock
import com.sillyapps.stockapp.domain.stock.model.StockEvent
import com.sillyapps.stockapp.features.main_screen.ui.model.MainScreenState
import kotlinx.coroutines.flow.Flow

interface StateHolder {

  fun getState(): Flow<MainScreenState>

  fun loadStockPrices(stockSymbols: List<String>)

  fun reload()

  fun getEventBus(): Flow<StockEvent>

}