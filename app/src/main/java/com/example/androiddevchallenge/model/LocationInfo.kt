package com.example.androiddevchallenge.model

import com.google.gson.annotations.SerializedName

data class LocationInfo(
    @SerializedName("items")
    var items : List<Location> = listOf()
)
