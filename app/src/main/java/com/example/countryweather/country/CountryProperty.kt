package com.example.countryweather.country

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "country_table")

data class CountryProperty(
        @PrimaryKey
        val name: String,
        val capital: String,
        val region: String,
        val population : Long,
        val flag : String

        ):Parcelable