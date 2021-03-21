package com.example.androiddevchallenge

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.model.LocationInfo
import com.example.androiddevchallenge.model.WeatherType
import com.google.gson.Gson
import java.util.Date

class WeatherInformationViewModel(
    var repository: WeatherDataRepository = WeatherDataRepository()
) : ViewModel() , LifecycleObserver {

    var locationPositions: List<String> by mutableStateOf(listOf("地点名"))

    var currentHorizontalValue by mutableStateOf(0.5F)
    var currentVerticalValue by mutableStateOf(0.5F)
    var currentHorizontalLine by mutableStateOf(1.0F)
    var currentVerticalLine by mutableStateOf(1.0F)

    var weatherDate by mutableStateOf(Date())
    private val initTemperatures = listOf<Double>()
    private val initWeatherTypes = listOf<WeatherType>()
    private val initChanceOfRain = listOf<Int>()

    var temperatures by mutableStateOf(initTemperatures)
    var weatherTypes by mutableStateOf(initWeatherTypes)
    var chanceOfRain by mutableStateOf(initChanceOfRain)

    init {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        horizontalValueChange(0.5F)
        verticalValueChange(0.5F)

        LocationSave.saveInfo.let {
            val info = Gson().fromJson<LocationInfo>(it, LocationInfo::class.java) as LocationInfo
            locationPositions = info.items.map { item -> item.locationName }
        }

        val data = repository.getWeatherData(locationPositions[0])
        weatherDate = data.date
        temperatures = data.temperatures
        weatherTypes = data.weatherTypes
        chanceOfRain = data.chanceOfRain
    }

    fun horizontalValueChange(newValue: Float){
        currentHorizontalValue = newValue
        val horizontalLine = newValue + newValue
        val lineValue = when {
            horizontalLine < 0.4 -> {
                0.4F
            }
            horizontalLine > 1.6 -> {
                1.6F
            }
            else -> {
                horizontalLine
            }
        }
        currentHorizontalLine = lineValue
    }

    fun verticalValueChange(newValue: Float){
        currentVerticalValue = newValue
        val verticalLine = newValue + newValue
        val lineValue = when {
            verticalLine < 0.4 -> {
                0.4F
            }
            verticalLine > 1.6 -> {
                1.6F
            }
            else -> {
                verticalLine
            }
        }
        currentVerticalLine = lineValue
    }


}