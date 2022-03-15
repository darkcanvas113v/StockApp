package com.sillyapps.stock.model

data class Stock(
  val symbol: String,

  val name: String,
  val price: StockPrice?
)