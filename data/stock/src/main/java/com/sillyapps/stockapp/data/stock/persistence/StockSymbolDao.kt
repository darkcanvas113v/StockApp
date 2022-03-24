package com.sillyapps.stockapp.data.stock.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sillyapps.stockapp.data.stock.persistence.models.StockSymbolEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StockSymbolDao {

  @Query("select * from stockSymbols")
  suspend fun getAll(): List<StockSymbolEntity>

  @Query("select exists(select * from stockSymbols)")
  suspend fun isCached(): Boolean

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(items: List<StockSymbolEntity>)

}