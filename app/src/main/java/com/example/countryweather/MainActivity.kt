package com.example.countryweather

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.countryweather.databinding.ActivityMainBinding
import com.google.android.gms.location.*
import java.util.*


class MainActivity : AppCompatActivity() {
//    lateinit var viewModel : mainActivityViewModel

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    val PERMISSION_ID : Int = 100

   private lateinit var  binding  : ActivityMainBinding
    var location : MutableLiveData<Location> = MutableLiveData(Location("dummy provider"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CountryWeather)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        val toolbar= binding.toolbar
        setSupportActionBar(toolbar)
        Log.d("Debug:", CheckPermission().toString())
        RequestPermission()
       if(!isLocationEnabled()){
           Toast.makeText(this,"Location is not enabled",Toast.LENGTH_SHORT).show()
       }
        NewLocationData()

        viewModel.properties.observe(this, {
            binding.cityName.text = it.name
            binding.temp.text = it.main.temp.toformatTemp()
            binding.extra.text = it.weather[0].description

        })



        location.observe(this, {
            viewModel.getCityProperties(it)
        })


    }
    fun Double.toformatTemp() : String{
        var temp = this- 273.15
         return  temp.format(0) +" °C"

    }
    fun Double.format(digits: Int) = "%.${digits}f".format(this)
    fun CheckPermission():Boolean{

        if(
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }

        return false

    }
    fun RequestPermission(){

        ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_ID
        )
    }


    fun isLocationEnabled():Boolean{

        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


    fun NewLocationData(){
        var locationRequest =  LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationProviderClient!!.requestLocationUpdates(
                locationRequest, locationCallback, Looper.myLooper()
        )
    }


    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation
            Log.d("Debug:", "your last last location: " + lastLocation.longitude.toString() + " " + lastLocation.latitude.toString())
            location?.value = lastLocation
//            textView.text = "You Last Location is : Long: "+ lastLocation.longitude + " , Lat: " + lastLocation.latitude + "\n" + getCityName(lastLocation.latitude,lastLocation.longitude)
        }
    }



}