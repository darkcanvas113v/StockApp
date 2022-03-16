package com.sillyapps.stockapp

import android.app.Application
import com.sillyapps.stockapp.data.stock.di.DaggerStockDataComponent
import com.sillyapps.stockapp.di.DaggerAppComponent
import kotlinx.coroutines.MainScope
import timber.log.Timber

class App: Application() {

  private val appComponent by lazy {
    DaggerAppComponent.create()
  }

  private val appScope = MainScope()

  val stockDataComponent by lazy {
    DaggerStockDataComponent.builder()
      .coroutineScope(appScope)
      .context(applicationContext)
      .build()
  }

  override fun onCreate() {
    initializeTimber()
    super.onCreate()
  }

  private fun initializeTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }

}