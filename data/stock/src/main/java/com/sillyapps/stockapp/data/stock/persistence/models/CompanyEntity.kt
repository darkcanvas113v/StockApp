package com.sillyapps.stockapp.data.stock.persistence.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sillyapps.stockapp.data.stock.remote.models.CompanyDto
import com.sillyapps.stockapp.domain.stock.model.Company

@Entity(tableName = "companies")
data class CompanyEntity(
  @PrimaryKey val symbol: String,
  val name: String?,
  val logoUrl: String,
  val country: String?,
  val marketCapitalization: Double?,
  val currency: String?,
  val weburl: String?,
  val industry: String?
)

fun CompanyEntity.toDomainModel(): Company {
  return Company(
    name = name,
    logoUrl = logoUrl,
    country = country,
    marketCapitalization = marketCapitalization,
    currency = currency,
    weburl = weburl,
    industry = industry
  )
}

fun CompanyDto.toDatabaseModel(symbol: String): CompanyEntity {
  return CompanyEntity(
    symbol = symbol,
    name = name,
    logoUrl = logo ?: "",
    country = country,
    marketCapitalization = marketCapitalization,
    currency = currency,
    industry = finnhubIndustry,
    weburl = weburl
  )
}