package com.sillyapps.stockapp.data.stock.remote.models.trades_websocket

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServerRequestDto(
  val type: String,
  val symbol: String
) {
  companion object Type {
    const val TYPE_SUBSCRIBE = "subscribe"
    const val TYPE_UNSUBSCRIBE = "unsubscribe"
  }
}