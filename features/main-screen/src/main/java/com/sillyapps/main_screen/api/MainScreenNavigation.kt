package com.sillyapps.main_screen.api

import androidx.compose.runtime.Composable
import com.sillyapps.core_ui.daggerViewModel
import com.sillyapps.main_screen.di.DaggerMainScreenComponent
import com.sillyapps.main_screen.ui.MainScreenViewModel
import com.sillyapps.stock.StockRepository

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



}