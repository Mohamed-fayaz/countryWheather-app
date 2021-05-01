package com.example.countryweather.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.countryweather.country.CountryProperty

@Dao
interface  CountryDao {
    @Query("SELECT * FROM  country_table")
    fun getAllCountries(): LiveData<List<CountryProperty>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCountries (CountryList: List<CountryProperty>)

    @Query("DELETE FROM COUNTRY_TABLE")
    suspend fun deleteAllCountries() : Int
}