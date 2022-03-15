package com.sillyapps.stockapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.sillyapps.stockapp.navigation.AppNavHost
import com.sillyapps.stockapp.ui.RootContainer

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      RootContainer()
    }
  }
}