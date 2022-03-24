package com.sillyapps.stockapp.data.stock.persistence.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sillyapps.stockapp.data.stock.remote.models.QuoteDto
import com.sillyapps.stockapp.domain.stock.model.Quote

@Entity(tableName = "quotes")
data class QuoteEntity(
  @PrimaryKey val symbol: String,
  val currentPrice: Double,
  val percentChange: Double?,
  val timestamp: Long
)

fun QuoteEntity.toDomainModel(): Quote {
  return Quote(
    currentPrice = currentPrice,
    percentChange = percentChange,
    timestamp = timestamp
  )
}

fun QuoteDto.toDatabaseModel(symbol: String): QuoteEntity {
  return QuoteEntity(
    symbol = symbol,
    currentPrice = currentPrice,
    percentChange = percentChange,
    timestamp = timeStamp
  )
}