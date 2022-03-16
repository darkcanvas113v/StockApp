package com.sillyapps.stockapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sillyapps.stockapp.features.main_screen.api.MainScreenNavigation
import com.sillyapps.stockapp.data.stock.di.StockDataComponent

@Composable
fun AppNavHost(
  stockDataComponent: StockDataComponent
) {
  val navController = rememberNavController()

  NavHost(
    navController = navController,
    startDestination = "main_screen"
  ) {
    composable(route = "main_screen") {
      MainScreenNavigation(
        repository = stockDataComponent.getRepository()
      )
    }
  }
}