package com.sillyapps.core_ui

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.os.Build
import android.os.Handler
import android.widget.Toast
import androidx.compose.ui.text.input.TextFieldValue

fun TextFieldValue.dataToString(): String {
  return "text = $text, selection = (${selection.start}, ${selection.end}), composition = $composition"
}

fun Boolean.int(): Int {
  return if (this) 1 else 0
}

fun Service.showToast(string: String) {
  val mainHandler = Handler(mainLooper)

  mainHandler.post {
    Toast.makeText(
      applicationContext,
      string,
      Toast.LENGTH_LONG).show()
  }
}

fun showToast(context: Context, string: String) {
  Toast.makeText(context, string, Toast.LENGTH_LONG).show()
}

fun getImmutablePendingIntentFlags(): Int {
  var piFlags = PendingIntent.FLAG_UPDATE_CURRENT
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    piFlags = piFlags or PendingIntent.FLAG_IMMUTABLE
  }
  return piFlags
}