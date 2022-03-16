package com.sillyapps.stockapp.features.main_screen.ui

import androidx.compose.runtime.State
import com.sillyapps.stockapp.features.main_screen.ui.model.MainScreenState

interface StateHolder {

  fun getState(): State<MainScreenState>

}