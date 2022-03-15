package com.sillyapps.stock

import com.sillyapps.core_di.modules.IOCoroutineScope
import com.sillyapps.core_di.modules.IODispatcher
import com.sillyapps.network.Resource
import com.sillyapps.stock.dto.toDomainModel
import com.sillyapps.stock.model.Stock
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
  @IOCoroutineScope private val ioScope: CoroutineScope,
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,

  private val finnhubApi: FinnhubApi,
): StockRepository {

  private val mStocks: MutableStateFlow<Resource<List<Stock>>> = MutableStateFlow(Resource.Loading())

  override fun getStocks(): Flow<Resource<List<Stock>>> {
    ioScope.launch(ioDispatcher) {
      try {
        val stocks = finnhubApi.getStocks()
        mStocks.value = Resource.Success(stocks.map { it.toDomainModel() })
      } catch (e: HttpException) {
        mStocks.value = (Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
      } catch (e: IOException) {
        mStocks.value = (Resource.Error("Couldn't reach server. Check your internet connection."))
      }
    }

    return mStocks
  }

}