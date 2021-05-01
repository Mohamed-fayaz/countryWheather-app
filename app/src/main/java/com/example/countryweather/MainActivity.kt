package com.example.countryweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.countryweather.listdata.ListViewModel
import com.example.countryweather.listdata.ListViewModelFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CountryWeather)
        setContentView(R.layout.activity_main)
    }
}