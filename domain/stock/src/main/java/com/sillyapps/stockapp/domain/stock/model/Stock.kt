package com.sillyapps.stockapp.domain.stock.model

data class Stock(
  val symbol: String,

  val name: String,
  val price: Double?
)