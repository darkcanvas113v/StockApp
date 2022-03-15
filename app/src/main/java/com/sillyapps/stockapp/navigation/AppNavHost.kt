package com.sillyapps.stockapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost() {
  val navController = rememberNavController()

  NavHost(
    navController = navController,
    startDestination = "main_screen"
  ) {
    composable("main_screen") {  }
  }
}