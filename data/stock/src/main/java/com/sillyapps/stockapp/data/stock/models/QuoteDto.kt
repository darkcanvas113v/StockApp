package com.sillyapps.stockapp.data.stock.models

import com.sillyapps.stockapp.domain.stock.model.Quote
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuoteDto(
  @Json(name = "c")
  val currentPrice: Double,

  @Json(name = "d")
  val change: Double?,

  @Json(name = "dp")
  val percentChange: Double?,

  @Json(name = "h")
  val highPriceOfTheDay: Double?,

  @Json(name = "l")
  val lowPriceOfTheDay: Double?,

  @Json(name = "o")
  val openPriceOfTheDay: Double?,

  @Json(name = "pc")
  val previousClosePrice: Double?,

  @Json(name = "t")
  val timeStamp: Long,
)

fun QuoteDto.toDomainModel(): Quote {
  return Quote(
    currentPrice = currentPrice,
    percentChange = percentChange
  )
}