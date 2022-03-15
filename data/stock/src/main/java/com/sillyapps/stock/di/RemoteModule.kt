package com.sillyapps.stock.di

import com.sillyapps.core_di.FeatureScope
import com.sillyapps.stock.FinnhubApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
object RemoteModule {

  @Provides
  @FeatureScope
  fun provideFinnhubApi(): FinnhubApi {
    return Retrofit.Builder()
      .baseUrl("https://finnhub.io/api/v1/")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(FinnhubApi::class.java)
  }

}