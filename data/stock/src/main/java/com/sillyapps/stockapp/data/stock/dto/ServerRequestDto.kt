package com.sillyapps.stockapp.data.stock.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServerRequestDto(
  val type: String,
  val symbol: String
)