/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import com.example.androiddevchallenge.model.WeatherData
import com.example.androiddevchallenge.model.WeatherType
import java.util.Date

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

    fun getWeatherMessage(locationName: String): String {
        return "The Pacific side of the country, including Tokyo, will continue to have dry and sunny weather.\n" +
            "Be careful with fire and prevent colds.\n" +
            "The following is the temperature from noon to night.\n" +
            "It will continue to be cold like January all over Japan.\n" +
            "The highest temperature in Tokyo is 11 degrees Celsius, and 8 degrees in Nagoya.\n" +
            "Lastly, here is the weekly forecast.\n" +
            "The Pacific side of the country, including Tokyo, will be sunny during the holidays.\n" +
            "This is the latest weather forecast released at 11:00 a.m."
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
