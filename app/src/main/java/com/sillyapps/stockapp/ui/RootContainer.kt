package com.sillyapps.stockapp.ui

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.sillyapps.stockapp.navigation.AppNavHost
import com.sillyapps.ui.theme.AppTheme

@Composable
fun RootContainer() {
  AppTheme {
    Surface {
      AppNavHost()
    }
  }
}