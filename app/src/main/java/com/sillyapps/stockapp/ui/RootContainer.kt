package com.sillyapps.stockapp.ui

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.sillyapps.stockapp.data.stock.di.StockDataComponent
import com.sillyapps.stockapp.navigation.AppNavHost
import com.sillyapps.stockapp.common.ui.theme.AppTheme

@Composable
fun RootContainer(
  stockDataComponent: StockDataComponent
) {
  AppTheme {
    Surface {
      AppNavHost(
        stockDataComponent = stockDataComponent)
    }
  }
}