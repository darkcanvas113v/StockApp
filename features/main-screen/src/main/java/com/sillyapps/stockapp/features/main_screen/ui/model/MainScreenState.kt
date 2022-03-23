package com.sillyapps.stockapp.features.main_screen.ui.model

import com.sillyapps.core_network.Resource
import com.sillyapps.stockapp.domain.stock.model.Quote
import com.sillyapps.stockapp.domain.stock.model.Stock

data class MainScreenState(
  val status: Status = Status.LOADING,
  val stocks: List<Stock>? = null,
  val error: Resource.Error<List<Stock>>? = null
) {
  enum class Status {
    LOADING, ERROR, READY
  }
}