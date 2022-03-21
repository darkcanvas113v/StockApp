package com.sillyapps.stockapp.features.main_screen.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sillyapps.stockapp.domain.stock.model.Stock
import timber.log.Timber

@Composable
fun DefaultFragment(
  items: List<Stock>,
  onLoadPrices: (List<String>) -> Unit
) {
  val listState = rememberLazyListState()

  var prevVisibleItemStart by remember {
    mutableStateOf(0)
  }
  var prevVisibleItemEnd by remember {
    mutableStateOf(0)
  }

  val visibleItemEnd =
    listState.firstVisibleItemIndex + listState.layoutInfo.visibleItemsInfo.size

  LazyColumn(
    contentPadding = PaddingValues(top = 16.dp),
    modifier = Modifier.fillMaxSize(),
    state = listState
  ) {

    if (!listState.isScrollInProgress &&
      (prevVisibleItemStart != listState.firstVisibleItemIndex || prevVisibleItemEnd != visibleItemEnd)) {
      onLoadPrices(
        listState.layoutInfo.visibleItemsInfo.map { items[it.index].symbol }
      )

      prevVisibleItemStart = listState.firstVisibleItemIndex
      prevVisibleItemEnd = visibleItemEnd
    }

    items(items = items) { stock ->
      StockItem(stock = stock)
    }
  }
}