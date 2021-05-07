package com.example.countryweather.detail

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.countryweather.country.CountryProperty
import com.example.countryweather.weather.WeatherApi
import com.example.countryweather.wheathermodel.City
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailViewModel(countryProperty: CountryProperty) : ViewModel() {
    private val _selectedProperty = MutableLiveData<CountryProperty>()
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<String>()

    val status: LiveData<String>
        get() = _status

    private val _properties = MutableLiveData<City>()

    val properties: LiveData<City>
        get() = _properties
    val selectedProperty: LiveData<CountryProperty>
        get() = _selectedProperty

    init {
        _selectedProperty.value = countryProperty
        var cityName = selectedProperty.value?.capital
        if (cityName != null) {
            getCityProperties(cityName)
        }

    }

    fun getCityProperties(cityName : String) {
        coroutineScope.launch {
            var getPropertiesDeferred = WeatherApi.retrofitService.getPropertyCity(cityName,"220b237732d260bd27bfe90ea42e5794")

            try {

                var Result = getPropertiesDeferred.await()
                _properties.value = Result
                Log.i("valueobtained capital",Result.toString())

            } catch (e: Exception) {
                _status.value = e.message
                Log.i("eroor msg ",e.message.toString())
            }

        }

    }
}
class DetailViewModelFactory(
        private val countryProperty: CountryProperty) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(countryProperty) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
