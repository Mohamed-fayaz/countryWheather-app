package com.example.countryweather

import android.app.Application
import com.example.countryweather.database.CountryDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class Countryapplication : Application() {

    val database by lazy { CountryDatabase.getInstance(this) }
    val repository by lazy { CountryRepository(database.countryDao) }

}