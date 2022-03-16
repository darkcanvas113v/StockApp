package com.sillyapps.stockapp.features.main_screen.ui.model

import com.sillyapps.stockapp.domain.stock.model.Stock

data class MainScreenState(
  val isLoading: Boolean = false,
  val stocks: List<Stock>? = null,
  val error: String = ""
)
