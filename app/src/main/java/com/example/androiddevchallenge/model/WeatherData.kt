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
package com.example.androiddevchallenge.model

import com.example.androiddevchallenge.R
import java.util.Date

sealed class WeatherType {

    abstract fun weatherIconRes(): Int

    object Fine : WeatherType() {
        override fun weatherIconRes(): Int {
            return R.drawable.ic_sun
        }
    }

    object Cloudy : WeatherType() {
        override fun weatherIconRes(): Int {
            return R.drawable.ic_cloud
        }
    }

    object CloudySun : WeatherType() {
        override fun weatherIconRes(): Int {
            return R.drawable.ic_cloud_sun
        }
    }

    object Rain : WeatherType() {
        override fun weatherIconRes(): Int {
            return R.drawable.ic_rain_light
        }
    }

    object Snow : WeatherType() {
        override fun weatherIconRes(): Int {
            return R.drawable.ic_snow_1
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
