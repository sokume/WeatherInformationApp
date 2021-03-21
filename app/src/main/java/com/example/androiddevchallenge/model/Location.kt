package com.example.androiddevchallenge.model

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("locationName")
    var locationName : String,
    @SerializedName("latitude")
    var latitude: String,
    @SerializedName("longitude")
    var longitude: String,
)
