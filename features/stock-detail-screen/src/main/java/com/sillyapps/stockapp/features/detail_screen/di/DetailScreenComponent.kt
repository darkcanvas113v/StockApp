package com.sillyapps.stockapp.features.detail_screen.di

import com.sillyapps.core_di.ScreenScope
import com.sillyapps.stockapp.domain.stock.StockRepository
import com.sillyapps.stockapp.features.detail_screen.ui.DetailScreenViewModel
import dagger.BindsInstance
import dagger.Component

@ScreenScope
@Component
interface DetailScreenComponent {

  fun getViewModel(): DetailScreenViewModel

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun repository(repository: StockRepository): Builder

    fun build(): DetailScreenComponent
  }

}