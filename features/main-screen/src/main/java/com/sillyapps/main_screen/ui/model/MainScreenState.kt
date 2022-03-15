package com.sillyapps.main_screen.ui.model

import com.sillyapps.stock.model.Stock
import java.lang.Error

data class MainScreenState(
  val isLoading: Boolean = false,
  val stocks: List<Stock>? = null,
  val error: String = ""
)
