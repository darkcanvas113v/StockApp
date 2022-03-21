package com.sillyapps.stockapp.domain.stock.model

import java.util.*

data class Company(
  val name: String?,
  val country: String?,
  val currency: String?,
  val logoUrl: String?,
  val marketCapitalization: Double?,
  val weburl: String?,
  val industry: String?
)