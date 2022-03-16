package com.sillyapps.stockapp

import android.app.Application
import com.sillyapps.stockapp.data.stock.di.DaggerStockDataComponent
import kotlinx.coroutines.MainScope

class App: Application() {

  private val appScope = MainScope()

  val stockDataComponent by lazy {
    DaggerStockDataComponent.builder()
      .coroutineScope(appScope)
      .context(applicationContext)
      .build()
  }

}