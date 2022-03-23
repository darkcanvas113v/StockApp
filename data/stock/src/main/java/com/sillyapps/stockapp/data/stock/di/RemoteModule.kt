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
import javax.inject.Qualifier

@Module
object RemoteModule {

  @AppScope
  @Provides
  fun provideFinnhubApi(client: OkHttpClient): FinnhubApi {
    return Retrofit.Builder()
      .baseUrl("https://finnhub.io/")
      .client(client)
      .addConverterFactory(MoshiConverterFactory.create())
      .build()
      .create(FinnhubApi::class.java)
  }

  @AppScope
  @Provides
  fun provideHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS

    return OkHttpClient.Builder()
      .addInterceptor(AddApiKeyInterceptor("c8qt8paad3i8tv0jrp50"))
      .addInterceptor(loggingInterceptor)
      .build()
  }

  class AddApiKeyInterceptor(private val key: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
      val originalRequest = chain.request()
      val url = originalRequest.url.newBuilder().addQueryParameter("token", key).build()
      val requestWithApiKey = originalRequest.newBuilder().url(url).build()

      return chain.proceed(requestWithApiKey)
    }

  }

}