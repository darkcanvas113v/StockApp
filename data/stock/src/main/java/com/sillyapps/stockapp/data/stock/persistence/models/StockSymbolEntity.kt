package com.sillyapps.stockapp.data.stock.persistence.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sillyapps.stockapp.data.stock.remote.models.StockSymbolDto
import com.sillyapps.stockapp.domain.stock.model.Stock

@Entity(tableName = "stockSymbols")
data class StockSymbolEntity(
  @PrimaryKey val symbol: String,
  val name: String
)

fun StockSymbolEntity.toDomainModel(): Stock {
  return Stock(
    symbol = symbol,
    name = name,
    company = null,
    quote = null
  )
}

fun StockSymbolDto.toDatabaseModel(): StockSymbolEntity {
  return StockSymbolEntity(
    symbol = symbol,
    name = description
  )
}