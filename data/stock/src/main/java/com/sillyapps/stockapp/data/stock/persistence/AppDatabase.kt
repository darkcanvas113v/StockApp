package com.sillyapps.stockapp.data.stock.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sillyapps.stockapp.data.stock.persistence.models.CompanyEntity
import com.sillyapps.stockapp.data.stock.persistence.models.QuoteEntity
import com.sillyapps.stockapp.data.stock.persistence.models.StockSymbolEntity

@Database(entities = [CompanyEntity::class, QuoteEntity::class, StockSymbolEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

  abstract val companyDao: CompanyDao

  abstract val quoteDao: QuoteDao

  abstract val stockSymbolDao: StockSymbolDao

  companion object {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
      synchronized(this) {
        var instance = INSTANCE

        if (instance == null) {
          instance = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database")
            .fallbackToDestructiveMigration()
            .build()

          INSTANCE = instance
        }

        return instance
      }
    }
  }

}