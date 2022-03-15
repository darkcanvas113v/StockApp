package com.sillyapps.main_screen.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sillyapps.main_screen.ui.model.MainScreenState
import com.sillyapps.network.Resource
import com.sillyapps.stock.StockRepository
import com.sillyapps.stock.usecases.GetStocksUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
  private val getStocksUseCase: GetStocksUseCase
): ViewModel(), StateHolder {

  private val _state = mutableStateOf(MainScreenState())

  init {
    viewModelScope.launch {
      getStocksUseCase().collect {
        when (it) {
          is Resource.Success -> {
            MainScreenState(stocks = it.data)
          }

          is Resource.Loading -> {
            _state.value = MainScreenState(isLoading = true)
          }

          is Resource.Error -> {
            _state.value = MainScreenState(
              error = it.message ?: "An unexpected error occured."
            )
          }
        }
      }
    }
  }

  override fun getState(): State<MainScreenState> = _state

}