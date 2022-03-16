package com.sillyapps.stockapp.data.stock.di

import com.sillyapps.core_di.FeatureScope
import com.sillyapps.stockapp.data.stock.FinnhubApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object RemoteModule {

  @Provides
  @FeatureScope
  fun provideFinnhubApi(): FinnhubApi {
    return Retrofit.Builder()
      .baseUrl("https://finnhub.io/api/v1/")
      .client(getHttpClient())
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(FinnhubApi::class.java)
  }

  private fun getHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder()
      .addInterceptor(AddApiKeyInterceptor())
      .addInterceptor(loggingInterceptor)
      .build()
  }

  class AddApiKeyInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
      val originalRequest = chain.request()
      val url = originalRequest.url.newBuilder().addQueryParameter("token", "sandbox_c8mv5g2ad3id1m4i6m20").build()
      val requestWithApiKey = originalRequest.newBuilder().url(url).build()

      return chain.proceed(requestWithApiKey)
    }

  }

}