package com.sillyapps.stockapp.data.stock.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServerMessageDto(
    val type: String,
    val data: List<StockTradeDto>? = null
)