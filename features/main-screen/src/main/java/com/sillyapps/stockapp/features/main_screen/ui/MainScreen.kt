package com.sillyapps.stockapp.features.main_screen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sillyapps.stockapp.features.main_screen.ui.model.MainScreenState
import com.sillyapps.stockapp.common.ui.compose.theme.AppTheme
import com.sillyapps.stockapp.features.main_screen.ui.components.DefaultFragment
import com.sillyapps.stockapp.features.main_screen.ui.components.ErrorScreen
import com.sillyapps.stockapp.features.main_screen.ui.components.LoadingFragment
import com.sillyapps.stockapp.features.main_screen.ui.preview.PreviewConstants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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
  val state = flow {
    emit(MainScreenState())
    delay(2000L)
    emit(PreviewConstants.previewScreenState)
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