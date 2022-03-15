package com.sillyapps.core_di.modules

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Qualifier
annotation class IODispatcher

@Qualifier
annotation class IOCoroutineScope

@Module
object IOModule {
  @Provides
  @IODispatcher
  fun provideIODispatcher(): CoroutineDispatcher {
    return Dispatchers.IO
  }
}