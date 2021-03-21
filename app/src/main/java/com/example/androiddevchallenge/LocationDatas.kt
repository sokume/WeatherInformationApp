package com.example.androiddevchallenge

import com.chibatching.kotpref.KotprefModel
import com.google.gson.annotations.SerializedName

object LocationSave : KotprefModel() {
    var saveInfo by stringPref()
}

data class LocationInfo(
    @SerializedName("items")
    var items : List<Location> = listOf()
)
data class Location(
    @SerializedName("locationName")
    var locationName : String,
    @SerializedName("latitude")
    var latitude: String,
    @SerializedName("longitude")
    var longitude: String,
)
