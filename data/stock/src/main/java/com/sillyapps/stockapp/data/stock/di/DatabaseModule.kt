package com.sillyapps.stockapp.data.stock.di

import android.content.Context
import com.sillyapps.core_di.AppScope
import com.sillyapps.stockapp.data.stock.persistence.AppDatabase
import com.sillyapps.stockapp.data.stock.persistence.CompanyDao
import com.sillyapps.stockapp.data.stock.persistence.QuoteDao
import com.sillyapps.stockapp.data.stock.persistence.StockSymbolDao
import dagger.Module
import dagger.Provides

@Module
object DatabaseModule {

  @AppScope
  @Provides
  fun provideDatabase(context: Context): AppDatabase = AppDatabase.getInstance(context)

  @AppScope
  @Provides
  fun provideCompanyDao(database: AppDatabase): CompanyDao = database.companyDao

  @AppScope
  @Provides
  fun provideQuoteDao(database: AppDatabase): QuoteDao = database.quoteDao

  @AppScope
  @Provides
  fun provideStockSymbolDao(database: AppDatabase): StockSymbolDao = database.stockSymbolDao

}