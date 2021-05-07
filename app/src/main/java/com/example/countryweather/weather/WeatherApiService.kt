package com.example.countryweather.weather

import com.example.countryweather.country.CountryProperty
import com.example.countryweather.wheathermodel.City
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "http://api.openweathermap.org/data/2.5/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()


interface WeatherApiService{


    @GET("weather/")
    fun getProperties( @Query("lat") lat:Double,@Query("lon") lon:Double,
                          @Query("appid") appId:String): Deferred<City>
    @GET("weather/")
    fun getPropertyCity( @Query("q") q:String,
                       @Query("appid") appId:String): Deferred<City>

}

object WeatherApi {
    val retrofitService : WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }}
