package com.sillyapps.stockapp.domain.stock.model

data class Quote(
  val currentPrice: Double,
  val percentChange: Double?,
  val timestamp: Long
)
