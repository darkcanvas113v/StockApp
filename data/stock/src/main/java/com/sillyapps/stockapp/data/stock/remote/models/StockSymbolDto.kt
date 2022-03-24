package com.sillyapps.stockapp.data.stock.remote.models

import com.sillyapps.stockapp.domain.stock.model.Stock
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StockSymbolDto(
    val currency: String,
    val description: String,
    val displaySymbol: String,
    val figi: String,
    val isin: Any?,
    val mic: String,
    val shareClassFIGI: String,
    val symbol: String,
    val symbol2: String,
    val type: String
)

fun StockSymbolDto.toDomainModel(): Stock {
    return Stock(
        name = description.ifBlank { symbol },
        symbol = symbol,
        quote = null,
        company = null
    )
}