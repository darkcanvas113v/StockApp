package com.sillyapps.stockapp.data.stock

import com.sillyapps.core_di.modules.IOCoroutineScope
import com.sillyapps.core_di.modules.IODispatcher
import com.sillyapps.core_network.Resource
import com.sillyapps.core_util.BiDirectionalMap
import com.sillyapps.core_util.modify
import com.sillyapps.stockapp.data.stock.models.ServerMessageDto
import com.sillyapps.stockapp.data.stock.models.ServerRequestDto
import com.sillyapps.stockapp.data.stock.models.toDomainModel
import com.sillyapps.stockapp.domain.stock.model.Stock
import com.sillyapps.stockapp.domain.stock.StockRepository
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
  @IOCoroutineScope private val ioScope: CoroutineScope,
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,

  private val finnhubApi: FinnhubApi,
  private val stockDataSource: StockDataSource
): StockRepository {

  private val connectionStatus: MutableStateFlow<Resource<Unit>> = MutableStateFlow(Resource.Loading())

  private val mStocksResource = connectionStatus.combine(stockDataSource.getStocks()) { status, stocks ->
    when (status) {
      is Resource.Loading -> Resource.Loading()
      is Resource.Success -> Resource.Success(stocks)
      is Resource.Error -> Resource.Error(status.message ?: "")
    }
  }

  override fun getStocks(): Flow<Resource<List<Stock>>> {
    ioScope.launch(ioDispatcher) {
      try {
        val stocks = finnhubApi.getStocks().map { it.toDomainModel() }

        connectionStatus.value = Resource.Success(Unit)
        stockDataSource.setInitialStocks(stocks)
      } catch (e: HttpException) {
        connectionStatus.value = (Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
      } catch (e: IOException) {
        connectionStatus.value = (Resource.Error("Couldn't reach server. Check your internet connection."))
      }
    }

    return mStocksResource
  }

  override suspend fun loadStockPrices(fromIndex: Int, toIndex: Int) {
    stockDataSource.loadStockPrices(fromIndex, toIndex)
  }


}