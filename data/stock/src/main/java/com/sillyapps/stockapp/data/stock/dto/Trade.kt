package com.sillyapps.stockapp.data.stock.dto

import com.google.gson.annotations.SerializedName

data class Trade(
    @SerializedName("c")
    val tradeConditions: List<String>,

    @SerializedName("p")
    val lastPrice: Double,

    @SerializedName("s")
    val symbol: String,

    @SerializedName("t")
    val timestamp: Long,

    @SerializedName("v")
    val volume: Double
)