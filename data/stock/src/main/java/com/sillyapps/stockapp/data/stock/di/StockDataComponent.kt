package com.sillyapps.stockapp.data.stock.di

import android.content.Context
import com.sillyapps.core_di.AppScope
import com.sillyapps.core_di.FeatureScope
import com.sillyapps.core_di.modules.IOCoroutineScope
import com.sillyapps.core_di.modules.IOModule
import com.sillyapps.stockapp.domain.stock.StockRepository
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient

@AppScope
@Component(
  modules = [IOModule::class, RemoteModule::class, RepositoryModule::class, DatabaseModule::class])
interface StockDataComponent {

  fun getRepository(): StockRepository

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun context(context: Context): Builder

    @BindsInstance
    fun coroutineScope(@IOCoroutineScope coroutineScope: CoroutineScope): Builder

    fun build(): StockDataComponent
  }

}