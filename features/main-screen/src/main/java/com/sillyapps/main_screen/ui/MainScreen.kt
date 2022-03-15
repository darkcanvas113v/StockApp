package com.sillyapps.main_screen.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sillyapps.main_screen.ui.components.StockItem
import com.sillyapps.main_screen.ui.model.MainScreenState
import com.sillyapps.network.Resource
import com.sillyapps.stock.model.Stock
import com.sillyapps.ui.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun MainScreen(
  stateHolder: StateHolder
) {
  val state by remember(stateHolder) {
    stateHolder.getState()
  }

  Surface(modifier = Modifier.fillMaxSize()) {

    if (state.isLoading) {
      CircularProgressIndicator()
      return@Surface
    }

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

@Preview
@Composable
fun MainScreenPreview() {
  val data = listOf(
    Stock(symbol = "APPL", name = "Apple", price = 410.0),
    Stock(symbol = "MICR", name = "Microsoft", price = 540.0),
    Stock(symbol = "TESL", name = "Tesla", price = 109.0),
    Stock(symbol = "AMAZ", name = "Amazon", price = 256.0),
  )

  val state = remember {
    mutableStateOf(
      MainScreenState(stocks = data)
    )
  }
  
  val stateHolder = object : StateHolder {
    override fun getState(): State<MainScreenState> = state
  }

  AppTheme {
    MainScreen(stateHolder = stateHolder)
  }

}