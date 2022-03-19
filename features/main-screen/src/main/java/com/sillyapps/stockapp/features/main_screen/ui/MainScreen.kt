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

  val listState = rememberLazyListState()

  var prevVisibleItemStart = remember {
    0
  }
  var prevVisibleItemEnd = remember {
    0
  }

  Surface(modifier = Modifier.fillMaxSize()) {
    Box() {
      when (state.status) {
        MainScreenState.Status.LOADING -> {
          CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
          )
        }
        MainScreenState.Status.ERROR -> {
          state.error?.let {
            when (it.type) {
              Resource.Error.Type.BAD_CONNECTION -> {
                Text(
                  text = "Couldn't reach server, please check your internet connection.",
                  modifier = Modifier.align(Alignment.Center)
                )
              }
              Resource.Error.Type.UNKNOWN -> {
                Text(
                  text = "Unknown error.",
                  modifier = Modifier.align(Alignment.Center)
                )
              }
            }
          }
        }
        MainScreenState.Status.READY -> {

          val visibleItemEnd =
            listState.firstVisibleItemIndex + listState.layoutInfo.visibleItemsInfo.size

          LazyColumn(
            contentPadding = PaddingValues(top = 16.dp),
            modifier = Modifier.fillMaxSize(),
            state = listState
          ) {
            val stocks = state.stocks ?: emptyList()

            if (!listState.isScrollInProgress &&
              (prevVisibleItemStart != listState.firstVisibleItemIndex || prevVisibleItemEnd != visibleItemEnd)) {
              stateHolder.loadStockPrices(
                listState.layoutInfo.visibleItemsInfo.map { stocks[it.index].symbol }
              )

              prevVisibleItemStart = listState.firstVisibleItemIndex
              prevVisibleItemEnd = visibleItemEnd

              Timber.e("First visible element: ${stocks[prevVisibleItemStart].name}. Size: ${listState.layoutInfo.totalItemsCount}")
            }

            items(items = stocks) { stock ->
              StockItem(stock = stock)
            }
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
    emit(MainScreenState())
    delay(2000L)
    emit(MainScreenState(stocks = data))
  }

  val stateHolder = object : StateHolder {
    override fun getState(): Flow<MainScreenState> = state

    override fun loadStockPrices(stockSymbols: List<String>) {

    }
  }

  AppTheme {
    MainScreen(stateHolder = stateHolder)
  }

}