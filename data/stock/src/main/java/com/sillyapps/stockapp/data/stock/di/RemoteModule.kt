package com.sillyapps.stockapp.data.stock.di

import com.sillyapps.core_di.AppScope
import com.sillyapps.core_di.FeatureScope
import com.sillyapps.stockapp.data.stock.FinnhubApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
object RemoteModule {

  @FeatureScope
  @Provides
  fun provideFinnhubApi(client: OkHttpClient): FinnhubApi {
    return Retrofit.Builder()
      .baseUrl("https://finnhub.io/api/v1/")
      .client(client)
      .addConverterFactory(MoshiConverterFactory.create())
      .build()
      .create(FinnhubApi::class.java)
  }

  @FeatureScope
  @Provides
  fun provideHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS

    return OkHttpClient.Builder()
      .addInterceptor(AddApiKeyInterceptor())
      .addInterceptor(loggingInterceptor)
      .build()
  }

  class AddApiKeyInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
      val originalRequest = chain.request()
      val url = originalRequest.url.newBuilder().addQueryParameter("token", "c8mv5g2ad3id1m4i6m1g").build()
      val requestWithApiKey = originalRequest.newBuilder().url(url).build()

      return chain.proceed(requestWithApiKey)
    }

  }

}