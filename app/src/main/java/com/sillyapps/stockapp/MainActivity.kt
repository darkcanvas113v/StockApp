package com.sillyapps.stockapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.sillyapps.stockapp.navigation.AppNavHost
import com.sillyapps.stockapp.ui.RootContainer

class MainActivity : AppCompatActivity() {

  private val repository by lazy { (application as App).stockDataComponent.getRepository() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val app = (application as App)

    setContent {
      RootContainer(app.stockDataComponent)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    repository.disconnect()
  }
}