package com.sillyapps.stockapp.data.stock.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StockTradeDto(
    @Json(name = "c")
    val tradeConditions: List<String>,

    @Json(name = "p")
    val lastPrice: Double,

    @Json(name = "s")
    val symbol: String,

    @Json(name = "t")
    val timestamp: Long,

    @Json(name = "v")
    val volume: Double
)