package com.sillyapps.stockapp.data.stock.remote.models

import com.sillyapps.stockapp.domain.stock.model.Company
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CompanyDto(
  val country: String?,
  val currency: String?,
  val exchange: String?,
  val finnhubIndustry: String?,
  val ipo: String?,
  val logo: String?,
  val marketCapitalization: Double?,
  val name: String?,
  val phone: String?,
  val shareOutstanding: Double?,
  val ticker: String?,
  val weburl: String?
)

fun CompanyDto.toDomainModel(): Company {
  return Company(
      name = name,
      country = country,
      currency = currency,
      logoUrl = logo ?: "",
      marketCapitalization = marketCapitalization,
      weburl = weburl,
      industry = finnhubIndustry
  )
}