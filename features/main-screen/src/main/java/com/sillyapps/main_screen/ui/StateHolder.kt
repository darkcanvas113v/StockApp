package com.sillyapps.main_screen.ui

import androidx.compose.runtime.State
import com.sillyapps.main_screen.ui.model.MainScreenState
import com.sillyapps.network.Resource
import com.sillyapps.stock.model.Stock
import kotlinx.coroutines.flow.Flow

interface StateHolder {

  fun getState(): State<MainScreenState>

}