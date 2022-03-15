package com.sillyapps.core_ui

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import androidx.core.content.ContextCompat

fun startForegroundServiceCompat(context: Context, intent: Intent) {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
    ContextCompat.startForegroundService(context, intent)
  else
    context.startService(intent)
}