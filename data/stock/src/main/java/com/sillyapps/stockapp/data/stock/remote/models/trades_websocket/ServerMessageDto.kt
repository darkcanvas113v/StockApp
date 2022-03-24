package com.sillyapps.stockapp.data.stock.remote.models.trades_websocket

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServerMessageDto(
    val type: String,
    val data: List<StockTradeDto>? = null
)