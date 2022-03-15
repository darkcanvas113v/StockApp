package com.sillyapps.main_screen.di

import com.sillyapps.core_di.ScreenScope
import com.sillyapps.main_screen.ui.MainScreenViewModel
import com.sillyapps.stock.StockRepository
import dagger.BindsInstance
import dagger.Component

@ScreenScope
@Component
interface MainScreenComponent {

  fun getViewModel(): MainScreenViewModel

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun repository(repository: StockRepository): Builder

    fun build(): MainScreenComponent
  }

}