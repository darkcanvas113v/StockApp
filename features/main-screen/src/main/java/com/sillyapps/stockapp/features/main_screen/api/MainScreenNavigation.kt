package com.sillyapps.stockapp.features.main_screen.api

import androidx.compose.runtime.Composable
import com.sillyapps.core_ui.daggerViewModel
import com.sillyapps.stockapp.features.main_screen.ui.MainScreen
import com.sillyapps.stockapp.features.main_screen.ui.MainScreenViewModel
import com.sillyapps.stockapp.domain.stock.StockRepository
import com.sillyapps.stockapp.features.main_screen.di.DaggerMainScreenComponent

@Composable
fun MainScreenNavigation(
  repository: StockRepository
) {

  val component = DaggerMainScreenComponent.builder()
    .repository(repository)
    .build()

  val viewModel: MainScreenViewModel = daggerViewModel {
    component.getViewModel()
  }
  
  MainScreen(stateHolder = viewModel)

}