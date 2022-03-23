package com.sillyapps.stockapp.data.stock.di

import com.sillyapps.core_di.AppScope
import com.sillyapps.core_di.FeatureScope
import com.sillyapps.stockapp.data.stock.StockDataSource
import com.sillyapps.stockapp.data.stock.StockDataSourceImpl
import com.sillyapps.stockapp.domain.stock.StockRepository
import com.sillyapps.stockapp.data.stock.StockRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

  @Binds
  @AppScope
  fun bindStockRepository(repositoryImpl: StockRepositoryImpl): StockRepository

  @Binds
  @AppScope
  fun bindStockDataSource(stockDataSourceImpl: StockDataSourceImpl): StockDataSource

}