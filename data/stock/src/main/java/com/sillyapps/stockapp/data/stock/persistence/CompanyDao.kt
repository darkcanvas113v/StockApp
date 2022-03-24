package com.sillyapps.stockapp.data.stock.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sillyapps.stockapp.data.stock.persistence.models.CompanyEntity

@Dao
interface CompanyDao {

  @Query("select * from companies where symbol = :symbol")
  suspend fun getCompany(symbol: String): CompanyEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(company: CompanyEntity)

}