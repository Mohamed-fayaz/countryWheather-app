package com.example.countryweather

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.countryweather.country.CountryProperty
import com.example.countryweather.database.CountryDao
import com.example.countryweather.database.CountryDatabase


class CountryRepository(val countryDao: CountryDao) {

//    private val _status = MutableLiveData<String>()
//
//    val status: LiveData<String>
//        get() = _status
//
//    private lateinit var _properties : LiveData<List<CountryProperty>>
//
//    // Room executes all queries on a separate thread.
//    // Observed Flow will notify the observer when the data has changed.
//    @SuppressLint("WrongThread")
//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread


}