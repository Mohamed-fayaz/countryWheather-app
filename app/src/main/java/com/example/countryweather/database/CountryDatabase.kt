package com.example.countryweather.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.countryweather.country.CountryProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [CountryProperty::class],version = 1,exportSchema = false)
abstract class CountryDatabase :RoomDatabase(){
    abstract val countryDao : CountryDao

    companion object{
        @Volatile
        private var INSTANCE: CountryDatabase? = null

        fun getInstance(context: Context): CountryDatabase {

            synchronized(this)  {
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CountryDatabase::class.java,
                        "Country_Database"
                    )
                        .fallbackToDestructiveMigration().build()
                }
                return instance
            }
        }
    }
}