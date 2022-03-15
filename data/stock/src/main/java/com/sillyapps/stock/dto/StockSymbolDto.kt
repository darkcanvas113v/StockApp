package com.sillyapps.stock.dto

import com.sillyapps.stock.model.Stock

data class StockSymbolDto(
    val currency: String,
    val description: String,
    val displaySymbol: String,
    val figi: String,
    val isin: Any,
    val mic: String,
    val shareClassFIGI: String,
    val symbol: String,
    val symbol2: String,
    val type: String
)

fun StockSymbolDto.toDomainModel(): Stock {
    return Stock(
        name = description,
        symbol = symbol,
        price = null
    )
}