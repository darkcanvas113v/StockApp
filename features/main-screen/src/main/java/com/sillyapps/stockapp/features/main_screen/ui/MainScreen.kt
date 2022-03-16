package com.sillyapps.stockapp.features.main_screen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sillyapps.stockapp.features.main_screen.ui.components.StockItem
import com.sillyapps.stockapp.features.main_screen.ui.model.MainScreenState
import com.sillyapps.stockapp.domain.stock.model.Stock
import com.sillyapps.stockapp.common.ui.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@Composable
fun MainScreen(
  stateHolder: StateHolder
) {
  val state by remember(stateHolder) {
    stateHolder.getState()
  }.collectAsState(initial = MainScreenState(isLoading = true))

  Surface(modifier = Modifier.fillMaxSize()) {
    Box() {
      if (state.isLoading) {
        CircularProgressIndicator(
          modifier = Modifier.align(Alignment.Center)
        )
      }
      else {
        LazyColumn(
          contentPadding = PaddingValues(top = 16.dp),
          modifier = Modifier.fillMaxSize()
        ) {
          items(items = state.stocks ?: emptyList()) { stock ->
            StockItem(stock = stock)
          }
        }
      }
    }
    
  }

}

@Preview
@Composable
fun MainScreenPreview() {
  val data = listOf(
    Stock(symbol = "APPL", name = "Apple", price = 410.0),
    Stock(symbol = "MICR", name = "Microsoft", price = 540.0),
    Stock(symbol = "TESL", name = "Tesla", price = 109.0),
    Stock(symbol = "AMAZ", name = "Amazon", price = 256.0),
  )

  val state = flow {
      emit(MainScreenState(isLoading = true))
      delay(2000L)
      emit(MainScreenState(stocks = data))
  }
  
  val stateHolder = object : StateHolder {
    override fun getState(): Flow<MainScreenState> = state
  }

  AppTheme {
    MainScreen(stateHolder = stateHolder)
  }

}