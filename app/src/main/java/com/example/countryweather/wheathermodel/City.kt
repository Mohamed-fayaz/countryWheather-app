package com.example.countryweather.wheathermodel

data class City(
    val weather:Array<Weather>,
    val main:Main,
    val wind:Wind,
    val name:String
)
