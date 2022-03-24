package com.sillyapps.stockapp.data.stock

import com.sillyapps.core_di.modules.IOCoroutineScope
import com.sillyapps.core_di.modules.IODispatcher
import com.sillyapps.core_network.Resource
import com.sillyapps.core_network.tryToLoad
import com.sillyapps.stockapp.data.stock.models.toDomainModel
import com.sillyapps.stockapp.domain.stock.model.Stock
import com.sillyapps.stockapp.domain.stock.StockRepository
import com.sillyapps.stockapp.domain.stock.model.StockEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
  @IOCoroutineScope private val ioScope: CoroutineScope,
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,

  private val finnhubApi: FinnhubApi,
  private val stockDataSource: StockDataSource,
  private val eventBusDataSource: EventBusDataSource,

) : StockRepository {

  private val connectionStatus: MutableStateFlow<Resource<Unit>> =
    MutableStateFlow(Resource.Loading())

  private val mStocksResource =
    connectionStatus.combine(stockDataSource.getStocks()) { status, stocks ->
      when (status) {
        is Resource.Loading -> Resource.Loading()
        is Resource.Success -> Resource.Success(stocks)
        is Resource.Error -> Resource.Error(status.type, status.message!!, stocks)
      }
    }

  override fun getStocks(): Flow<Resource<List<Stock>>> {
    ioScope.launch(ioDispatcher) {
      connectionStatus.value = Resource.Loading()

      tryToLoad(
        tryBlock = {
          val stocks = finnhubApi.getStocks().map { it.toDomainModel() }
          stockDataSource.setInitialStocks(stocks)
          connectionStatus.value = Resource.Success(Unit)
        },
        onIOException = {
          connectionStatus.value = Resource.Error(
            type = Resource.Error.Type.UNKNOWN,
            message = it.localizedMessage ?: "An unexpected error occured"
          )
        },
        onHttpException = {
          connectionStatus.value = Resource.Error(
            type = Resource.Error.Type.BAD_CONNECTION,
            message = "Couldn't reach server. Check your internet connection."
          )
        }
      )
    }

    return mStocksResource
  }

  override suspend fun loadStockPrices(stockSymbols: List<String>) {
    stockDataSource.loadStockPrices(stockSymbols)
  }

  override fun disconnect() {
    stockDataSource.disconnect()
  }

  override fun getEventBus(): Flow<StockEvent> = eventBusDataSource.get()


}