package com.sillyapps.stockapp.features.main_screen.ui.preview

import com.sillyapps.stockapp.domain.stock.model.Quote
import com.sillyapps.stockapp.domain.stock.model.Stock
import com.sillyapps.stockapp.features.main_screen.ui.model.MainScreenState

object PreviewConstants {

  val previewStocks = listOf(
    Stock(
      symbol = "APPL",
      name = "Apple",
      quote = Quote(currentPrice = 410.0, percentChange = 2.0, 0),
      company = null
    ),
    Stock(symbol = "MICR",
      name = "Microsoft",
      quote = Quote(currentPrice = 410.0, percentChange = 2.0, 0),
      company = null),
    Stock(
      symbol = "TESL",
      name = "Tesla",
      quote = Quote(currentPrice = 410.0, percentChange = 2.0, 0),
      company = null),
    Stock(
      symbol = "AMAZ",
      name = "Amazon",
      quote = Quote(currentPrice = 410.0, percentChange = 2.0, 0),
      company = null),
  )

  val previewScreenState = MainScreenState(
    status = MainScreenState.Status.READY,
    stocks = previewStocks
  )

}