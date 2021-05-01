package com.example.countryweather.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.countryweather.country.CountryProperty

class DetailViewModel(countryProperty: CountryProperty) : ViewModel() {
    private val _selectedProperty = MutableLiveData<CountryProperty>()

    val selectedProperty: LiveData<CountryProperty>
        get() = _selectedProperty

    init {
        _selectedProperty.value = countryProperty
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
