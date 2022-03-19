package com.sillyapps.stockapp.features.main_screen.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sillyapps.stockapp.features.main_screen.ui.model.MainScreenState
import com.sillyapps.core_network.Resource
import com.sillyapps.stockapp.domain.stock.usecases.GetStocksUseCase
import com.sillyapps.stockapp.domain.stock.usecases.LoadStocksPriceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
  private val getStocksUseCase: GetStocksUseCase
): ViewModel(), StateHolder {

  private val _state = MutableStateFlow(MainScreenState())

  init {
    viewModelScope.launch {
      getStocksUseCase().collect {
        when (it) {
          is Resource.Success -> {
            _state.value = MainScreenState(
              status = MainScreenState.Status.READY,
              stocks = it.data
            )
          }

          is Resource.Loading -> {
            _state.value = MainScreenState(
              status = MainScreenState.Status.LOADING
            )
          }

          is Resource.Error -> {
            _state.value = MainScreenState(
              status = MainScreenState.Status.ERROR,
              error = it
            )
          }
        }
      }
    }
  }

  override fun getState() = _state

}