package com.example.countryweather.listdata

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.countryweather.CountryApi
import com.example.countryweather.CountryRepository
import com.example.countryweather.country.CountryProperty
import com.example.countryweather.database.CountryDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ListViewModel(val dataSource: CountryDao) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<String>()

    val status: LiveData<String>
        get() = _status

 private  var _properties  : LiveData<List<CountryProperty>> = dataSource.getAllCountries()

    val properties: LiveData<List<CountryProperty>>?
        get() = _properties

    init {
      getCountryProperties()
    }
    private val _navigateToSelectedProperty = MutableLiveData<CountryProperty?>()

    val navigateToSelectedProperty: MutableLiveData<CountryProperty?>
        get() = _navigateToSelectedProperty

    fun displayPropertyDetails(countryProperty: CountryProperty) {
        _navigateToSelectedProperty.value = countryProperty
    }


    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
     fun getCountryProperties() {
        coroutineScope.launch {
            val initialState: LiveData<List<CountryProperty>> = dataSource.getAllCountries()
            if (initialState.value == null) {
                var getPropertiesDeferred = CountryApi.retrofitService.getProperties()

                try {

                    var listResult = getPropertiesDeferred.await()
                    Log.i("val Msg", listResult.toString())

                    insert(listResult)

                } catch (e: Exception) {
                    _status.value = e.message
                    e.message?.let { Log.i("Error Msg", it) }
                }
            }
        }
    }

  fun insert(countryList :List<CountryProperty>) {
      coroutineScope.launch {
        dataSource.insertAllCountries(countryList)}

    }

}



class ListViewModelFactory(val dataSource: CountryDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}