package com.sillyapps.stockapp.data.stock.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sillyapps.stockapp.data.stock.persistence.models.CompanyEntity
import com.sillyapps.stockapp.data.stock.persistence.models.QuoteEntity
import com.sillyapps.stockapp.domain.stock.model.Quote

@Dao
interface QuoteDao {

  @Query("select * from quotes where symbol = :symbol")
  suspend fun getCachedQuote(symbol: String): QuoteEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(quote: QuoteEntity)

}