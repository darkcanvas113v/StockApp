package com.sillyapps.stock.di

import com.sillyapps.core_di.FeatureScope
import com.sillyapps.stock.StockRepository
import com.sillyapps.stock.StockRepositoryImpl
import dagger.Module

@Module
interface RepositoryModule {

  @FeatureScope
  fun bindStockRepository(repositoryImpl: StockRepositoryImpl): StockRepository

}