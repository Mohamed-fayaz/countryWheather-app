package com.example.countryweather

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.countryweather.weather.WeatherApi
import com.example.countryweather.wheathermodel.City
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel(){
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<String>()

    val status: LiveData<String>
        get() = _status

    private val _properties = MutableLiveData<City>()

    val properties: LiveData<City>
        get() = _properties
    init {

    }

fun getCityProperties(location: Location) {
        coroutineScope.launch {
            var getPropertiesDeferred = WeatherApi.retrofitService.getProperties(location.latitude,location.longitude ,"220b237732d260bd27bfe90ea42e5794")

            try {

                var Result = getPropertiesDeferred.await()
                _properties.value = Result
                Log.i("valueobtained",Result.toString())

            } catch (e: Exception) {
                _status.value = e.message
                Log.i("eroor msg ",e.message.toString())
            }

        }

    }
}

