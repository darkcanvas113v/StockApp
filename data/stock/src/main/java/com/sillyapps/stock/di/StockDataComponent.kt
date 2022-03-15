package com.sillyapps.stock.di

import android.content.Context
import com.sillyapps.core_di.FeatureScope
import com.sillyapps.core_di.modules.IOCoroutineScope
import com.sillyapps.core_di.modules.IOModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import kotlinx.coroutines.CoroutineScope
import javax.inject.Scope

@FeatureScope
@Component(modules = [IOModule::class, RemoteModule::class, RepositoryModule::class])
interface StockDataComponent {

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun context(context: Context): Builder

    @BindsInstance
    fun coroutineScope(@IOCoroutineScope coroutineScope: CoroutineScope): Builder

    fun build(): StockDataComponent
  }

}