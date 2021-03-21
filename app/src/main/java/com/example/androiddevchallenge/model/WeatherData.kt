package com.example.androiddevchallenge.model

import com.example.androiddevchallenge.R
import java.util.Date

sealed class WeatherType {

    abstract fun WeatherIconRes(): Int

    object Fine : WeatherType() {
        override fun WeatherIconRes(): Int {
            return R.drawable.ic_sun
        }
    }

    object Cloudy : WeatherType() {
        override fun WeatherIconRes(): Int {
            return R.drawable.ic_cloud
        }
    }

    object CloudySun : WeatherType() {
        override fun WeatherIconRes(): Int {
            return R.drawable.ic_cloud
        }
    }

    object Rain : WeatherType() {
        override fun WeatherIconRes(): Int {
            return R.drawable.ic_cloud
        }
    }

    object Snow : WeatherType() {
        override fun WeatherIconRes(): Int {
            return R.drawable.ic_cloud
        }
    }
}

data class WeatherData(
    val locationName: String,
    val date: Date,
    val temperatures: List<Double>,
    val weatherTypes: List<WeatherType>,
    val chanceOfRain: List<Int>,
)