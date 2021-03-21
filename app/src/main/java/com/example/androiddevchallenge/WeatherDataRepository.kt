package com.example.androiddevchallenge

import com.example.androiddevchallenge.model.WeatherData
import com.example.androiddevchallenge.model.WeatherType
import java.util.Date
import kotlin.random.Random

class WeatherDataRepository {

    fun getWeatherData(locationName: String): WeatherData {
        val dummy = makeDummy()
        return WeatherData(
            locationName = locationName,
            date = Date(),
            temperatures = dummy.first,
            weatherTypes = dummy.second,
            chanceOfRain = dummy.third
        )
    }

    private fun makeDummy(): Triple<List<Double>, List<WeatherType>, List<Int>> {
        val temperatures = listOf<Double>(
            (12..14).random().toDouble(),
            (12..14).random().toDouble(),
            (12..14).random().toDouble(),
            (12..14).random().toDouble(),
            (12..14).random().toDouble(),
            (12..14).random().toDouble(),
            (13..17).random().toDouble(),
            (13..17).random().toDouble(),
            (13..17).random().toDouble(),
            (13..17).random().toDouble(),
            (13..17).random().toDouble(),
            (13..17).random().toDouble(),
            (16..18).random().toDouble(),
            (16..18).random().toDouble(),
            (16..18).random().toDouble(),
            (16..18).random().toDouble(),
            (16..18).random().toDouble(),
            (16..18).random().toDouble(),
            (13..17).random().toDouble(),
            (13..17).random().toDouble(),
            (13..17).random().toDouble(),
            (13..17).random().toDouble(),
            (13..17).random().toDouble(),
            (13..17).random().toDouble(),
        )
        val weatherTypes = listOf<WeatherType>(
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
            makeDummyWeatherType((1..5).random()),
        )

        val chanceOfRain = listOf<Int>(
            (0..30).random() / 10 * 10,
            (0..30).random() / 10 * 10,
            (0..30).random() / 10 * 10,
            (0..30).random() / 10 * 10,
            (0..30).random() / 10 * 10,
            (0..30).random() / 10 * 10,
            (20..40).random() / 10 * 10,
            (20..40).random() / 10 * 10,
            (20..40).random() / 10 * 10,
            (20..40).random() / 10 * 10,
            (20..40).random() / 10 * 10,
            (20..40).random() / 10 * 10,
            (30..50).random() / 10 * 10,
            (30..50).random() / 10 * 10,
            (30..50).random() / 10 * 10,
            (30..50).random() / 10 * 10,
            (30..50).random() / 10 * 10,
            (30..50).random() / 10 * 10,
            (10..40).random() / 10 * 10,
            (10..40).random() / 10 * 10,
            (10..40).random() / 10 * 10,
            (10..40).random() / 10 * 10,
            (10..40).random() / 10 * 10,
            (10..40).random() / 10 * 10,
        )
        return Triple(temperatures, weatherTypes, chanceOfRain)
    }

    private fun makeDummyWeatherType(typeNum: Int): WeatherType {
        return when (typeNum) {
            1 -> WeatherType.CloudySun
            2 -> WeatherType.Snow
            3 -> WeatherType.Cloudy
            4 -> WeatherType.Fine
            5 -> WeatherType.Rain
            else -> WeatherType.CloudySun
        }
    }
}