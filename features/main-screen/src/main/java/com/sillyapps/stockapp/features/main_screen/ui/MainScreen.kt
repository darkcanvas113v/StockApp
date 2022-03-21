package com.sillyapps.stockapp.features.main_screen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sillyapps.core_network.Resource
import com.sillyapps.stockapp.features.main_screen.ui.components.StockItem
import com.sillyapps.stockapp.features.main_screen.ui.model.MainScreenState
import com.sillyapps.stockapp.domain.stock.model.Stock
import com.sillyapps.stockapp.common.ui.theme.AppTheme
import com.sillyapps.stockapp.domain.stock.model.Quote
import com.sillyapps.stockapp.features.main_screen.ui.components.DefaultFragment
import com.sillyapps.stockapp.features.main_screen.ui.components.ErrorScreen
import com.sillyapps.stockapp.features.main_screen.ui.components.LoadingFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

@Composable
fun MainScreen(
  stateHolder: StateHolder
) {
  val state by remember(stateHolder) {
    stateHolder.getState()
  }.collectAsState(initial = MainScreenState())

  Surface(modifier = Modifier.fillMaxSize()) {
    Box(contentAlignment = Alignment.Center) {
      when (state.status) {
        MainScreenState.Status.LOADING -> {
          LoadingFragment()
        }
        MainScreenState.Status.ERROR -> {
          ErrorScreen(
            error = state.error!!,
            onReload = stateHolder::reload
          )
        }
        MainScreenState.Status.READY -> {
          DefaultFragment(
            items = state.stocks ?: emptyList(),
            onLoadPrices = stateHolder::loadStockPrices)
        }
      }
    }

  }

}

@Preview
@Composable
fun MainScreenPreview() {
  val data = listOf(
    Stock(
      symbol = "APPL",
      name = "Apple",
      quote = Quote(currentPrice = 410.0, percentChange = 2.0),
      company = null
    ),
    Stock(symbol = "MICR",
      name = "Microsoft",
      quote = Quote(currentPrice = 410.0, percentChange = 2.0),
      company = null),
    Stock(
      symbol = "TESL",
      name = "Tesla",
      quote = Quote(currentPrice = 410.0, percentChange = 2.0),
      company = null),
    Stock(
      symbol = "AMAZ",
      name = "Amazon",
      quote = Quote(currentPrice = 410.0, percentChange = 2.0),
      company = null),
  )

  val state = flow {
    emit(MainScreenState())
    delay(2000L)
    emit(MainScreenState(stocks = data))
  }

  val stateHolder = object : StateHolder {
    override fun getState(): Flow<MainScreenState> = state

    override fun loadStockPrices(stockSymbols: List<String>) {

    }

    override fun reload() {

    }
  }

  AppTheme {
    MainScreen(stateHolder = stateHolder)
  }

}