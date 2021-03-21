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

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SwapHoriz
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.chibatching.kotpref.Kotpref
import com.example.androiddevchallenge.model.Location
import com.example.androiddevchallenge.model.LocationInfo
import com.example.androiddevchallenge.model.WeatherType
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.google.gson.Gson
import java.util.Date
import java.util.Locale
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import java.text.SimpleDateFormat

private val viewModel = WeatherInformationViewModel()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Kotpref.init(this)
        initializeData()

        setContent {
            MyTheme {
                MyApp()
            }
        }
        lifecycle.addObserver(viewModel)
    }

    private fun initializeData() {
        val loadDates = LocationSave.saveInfo
        if (loadDates.isEmpty()) {
            val items = listOf(
                Location(
                    locationName = "Nagoya",
                    latitude = "35.1855875",
                    longitude = "136.8990919"
                ),
                Location(
                    locationName = "Tokyo",
                    latitude = "35.1855875",
                    longitude = "136.8990919"
                ),
                Location(
                    locationName = "Osaka",
                    latitude = "35.1855875",
                    longitude = "136.8990919"
                )
            )
            val locationInfo = LocationInfo(items)
            val locationsString = Gson().toJson(locationInfo)
            LocationSave.saveInfo = locationsString
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {

    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar(
                title = { Text(text = "WeatherInformationApp") }
            )
            LocationTab(
                Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                viewModel
            )
            Row(
                modifier = Modifier.weight(4.0f, true),
            ) {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),

                    ) {
                    WeatherInformation(
                        viewModel = viewModel
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(96.dp)
                        ) {
                            VerticalSlider()
                            HorizontalSlider()
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.weight(2.0f, true),
            ) {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(Color.White),
                ) {
                }
            }
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}

@Composable
fun LocationTab(
    modifier: Modifier = Modifier,
    viewModel: WeatherInformationViewModel
) {

    var state = remember { mutableStateOf(0) }
    val titles = viewModel.locationPositions
    ScrollableTabRow(
        selectedTabIndex = state.value,
        modifier = Modifier.wrapContentWidth(),
        edgePadding = 16.dp,
    ) {
        titles.forEachIndexed { index, title ->
            Tab(
                selected = state.value == index,
                onClick = { state.value = index }) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp)
                )
            }
        }
    }
}

@Composable
fun WeatherInformation(
    modifier: Modifier = Modifier,
    viewModel: WeatherInformationViewModel
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 16.dp, bottom = 96.dp, end = 16.dp),
    ) {
        val vLine = viewModel.currentVerticalLine
        val hLine = viewModel.currentHorizontalLine
        val itemWidth = maxWidth / 2
        val itemHeight = maxHeight / 2
        val leftTopWidth = itemWidth * hLine
        val leftTopHeight = itemHeight * vLine
        val rightTopWidth = itemWidth * (2.0F - hLine)
        val rightTopHeight = itemHeight * vLine
        val leftBottomWidth = itemWidth * hLine
        val leftBottomHeight = itemHeight * (2.0F - vLine)
        val rightBottomWidth = itemWidth * (2.0F - hLine)
        val rightBottomHeight = itemHeight * (2.0F - vLine)


        Column() {
            Row() {
                Box(
                    Modifier
                        .width(leftTopWidth)
                        .height(leftTopHeight)
                ) {
                    WeatherDayView(viewModel.weatherDate, leftTopWidth, leftTopHeight)
                }
                Box(
                    Modifier
                        .width(rightTopWidth)
                        .height(rightTopHeight)
                ) {
                    WeatherTypeView(viewModel.weatherTypes, rightTopWidth, rightTopHeight)
                }
            }
            Row() {

                Box(
                    Modifier
                        .width(leftBottomWidth)
                        .height(leftBottomHeight)
                ) {
                    WeatherTemperaturesView(
                        viewModel.temperatures,
                        leftBottomWidth,
                        leftBottomHeight
                    )
                }
                Box(
                    Modifier
                        .width(rightBottomWidth)
                        .height(rightBottomHeight)
                ) {
                    WeatherChanceOfRainView(
                        viewModel.chanceOfRain,
                        rightBottomWidth,
                        rightBottomHeight
                    )
                }
            }
        }
    }
}

@Composable
fun VerticalSlider() {
    val value = viewModel.currentVerticalValue
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Icon(
            Icons.Rounded.SwapVert, "",
            Modifier.size(48.dp)
        )
        Slider(
            value = value,
            onValueChange = { newValue -> viewModel.verticalValueChange(newValue) },
        )
    }
}

@Composable
fun HorizontalSlider() {
    val value = viewModel.currentHorizontalValue
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Icon(
            Icons.Rounded.SwapHoriz, "",
            Modifier.size(48.dp)
        )
        Slider(
            value = value,
            onValueChange = { newValue -> viewModel.horizontalValueChange(newValue) },
        )
    }
}

@Composable
fun WeatherDayView(weatherDate: Date, width: Dp, height: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp)
            .clip(RoundedCornerShape(2.dp))
            .border(width = 2.dp, color = MaterialTheme.colors.secondary),
        contentAlignment = Alignment.Center
    ) {
        val formatPatten = when {
            width < 100.dp -> "dd"
            width < 200.dp -> "MM/dd"
            else -> "MM/dd, E"
        }
        val format = SimpleDateFormat(formatPatten, Locale.getDefault())
        val dateString = format.format(weatherDate)
        Text(text = dateString, style = MaterialTheme.typography.h3)
    }
}

@Composable
fun WeatherTypeView(types: List<WeatherType>, width: Dp, height: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp)
            .clip(RoundedCornerShape(2.dp))
            .border(width = 2.dp, color = MaterialTheme.colors.secondary),
        contentAlignment = Alignment.Center
    ) {
        val format = SimpleDateFormat("HH", Locale.getDefault())
        var currentHour = format.format(Date()).toInt()
        when {
            height < 100.dp -> {
                Column() {
                    WeatherTypeDetail(currentHour, types[0], width)
                }
            }
            height < 150.dp -> {
                Column() {
                    WeatherTypeDetail(currentHour, types[0], width)
                    currentHour++
                    WeatherTypeDetail(currentHour, types[1], width)
                }
            }
            height < 200.dp -> {
                Column() {
                    WeatherTypeDetail(currentHour, types[0], width)
                    currentHour++
                    WeatherTypeDetail(currentHour, types[1], width)
                    currentHour++
                    WeatherTypeDetail(currentHour, types[2], width)
                }
            }
            height < 250.dp -> {
                Column() {
                    WeatherTypeDetail(currentHour, types[0], width)
                    currentHour++
                    WeatherTypeDetail(currentHour, types[1], width)
                    currentHour++
                    WeatherTypeDetail(currentHour, types[2], width)
                    currentHour++
                    WeatherTypeDetail(currentHour, types[3], width)
                }
            }
            height < 300.dp -> {
                Column() {
                    WeatherTypeDetail(currentHour, types[0], width)
                    currentHour++
                    WeatherTypeDetail(currentHour, types[1], width)
                    currentHour++
                    WeatherTypeDetail(currentHour, types[2], width)
                    currentHour++
                    WeatherTypeDetail(currentHour, types[3], width)
                    currentHour++
                    WeatherTypeDetail(currentHour, types[4], width)
                }
            }
            else -> {
                Column() {
                    WeatherTypeDetail(currentHour, types[0], width)
                    currentHour++
                    WeatherTypeDetail(currentHour, types[1], width)
                    currentHour++
                    WeatherTypeDetail(currentHour, types[2], width)
                    currentHour++
                    WeatherTypeDetail(currentHour, types[3], width)
                    currentHour++
                    WeatherTypeDetail(currentHour, types[4], width)
                    currentHour++
                    WeatherTypeDetail(currentHour, types[5], width)
                }
            }
        }
    }
}

@Composable
fun WeatherTypeDetail(hour: Int, type: WeatherType, width: Dp) {
    val image: Painter = painterResource(id = type.WeatherIconRes())
    val text = if (width < 150.dp) {
        "$hour"
    } else {
        "$hour:00 : "
    }
    Row() {
        Text(text = text, style = MaterialTheme.typography.h4, textAlign = TextAlign.Center)
        Image(image, "", modifier = Modifier.size(40.dp), contentScale = ContentScale.Fit)
    }
}

@Composable
fun WeatherTemperaturesView(temperatures: List<Double>, width: Dp, height: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp)
            .clip(RoundedCornerShape(2.dp))
            .border(width = 2.dp, color = MaterialTheme.colors.secondary),
        contentAlignment = Alignment.Center
    ) {
        val format = SimpleDateFormat("HH", Locale.getDefault())
        var currentHour = format.format(Date()).toInt()
        when {
            height < 100.dp -> {
                Column() {
                    WeatherTemperaturesDetail(currentHour, temperatures[0], width)
                }
            }
            height < 150.dp -> {
                Column() {
                    WeatherTemperaturesDetail(currentHour, temperatures[0], width)
                    currentHour++
                    WeatherTemperaturesDetail(currentHour, temperatures[1], width)
                }
            }
            height < 200.dp -> {
                Column() {
                    WeatherTemperaturesDetail(currentHour, temperatures[0], width)
                    currentHour++
                    WeatherTemperaturesDetail(currentHour, temperatures[1], width)
                    currentHour++
                    WeatherTemperaturesDetail(currentHour, temperatures[2], width)
                }
            }
            height < 250.dp -> {
                Column() {
                    WeatherTemperaturesDetail(currentHour, temperatures[0], width)
                    currentHour++
                    WeatherTemperaturesDetail(currentHour, temperatures[1], width)
                    currentHour++
                    WeatherTemperaturesDetail(currentHour, temperatures[2], width)
                    currentHour++
                    WeatherTemperaturesDetail(currentHour, temperatures[3], width)
                }
            }
            height < 300.dp -> {
                Column() {
                    WeatherTemperaturesDetail(currentHour, temperatures[0], width)
                    currentHour++
                    WeatherTemperaturesDetail(currentHour, temperatures[1], width)
                    currentHour++
                    WeatherTemperaturesDetail(currentHour, temperatures[2], width)
                    currentHour++
                    WeatherTemperaturesDetail(currentHour, temperatures[3], width)
                    currentHour++
                    WeatherTemperaturesDetail(currentHour, temperatures[4], width)
                }
            }
            else -> {
                Column() {
                    WeatherTemperaturesDetail(currentHour, temperatures[0], width)
                    currentHour++
                    WeatherTemperaturesDetail(currentHour, temperatures[1], width)
                    currentHour++
                    WeatherTemperaturesDetail(currentHour, temperatures[2], width)
                    currentHour++
                    WeatherTemperaturesDetail(currentHour, temperatures[3], width)
                    currentHour++
                    WeatherTemperaturesDetail(currentHour, temperatures[4], width)
                    currentHour++
                    WeatherTemperaturesDetail(currentHour, temperatures[5], width)
                }
            }
        }
    }
}

@Composable
fun WeatherTemperaturesDetail(hour: Int, temperature: Double, width: Dp) {
    val text = if (width < 150.dp) {
        "$hour : $temperature"
    } else {
        "$hour:00 : $temperature"
    }
    Row() {
        Text(text = text, style = MaterialTheme.typography.h4, textAlign = TextAlign.Center)
    }
}

@Composable
fun WeatherChanceOfRainView(chanceOfRain: List<Int>, width: Dp, height: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp)
            .clip(RoundedCornerShape(2.dp))
            .border(width = 2.dp, color = MaterialTheme.colors.secondary),
        contentAlignment = Alignment.Center

    ) {
        val format = SimpleDateFormat("HH", Locale.getDefault())
        var currentHour = format.format(Date()).toInt()
        when {
            height < 100.dp -> {
                Column() {
                    WeatherChanceOfRainDetail(currentHour, chanceOfRain[0], width)
                }
            }
            height < 150.dp -> {
                Column() {
                    WeatherChanceOfRainDetail(currentHour, chanceOfRain[0], width)
                    currentHour++
                    WeatherChanceOfRainDetail(currentHour, chanceOfRain[1], width)
                }
            }
            height < 200.dp -> {
                Column() {
                    WeatherChanceOfRainDetail(currentHour, chanceOfRain[0], width)
                    currentHour++
                    WeatherChanceOfRainDetail(currentHour, chanceOfRain[1], width)
                    currentHour++
                    WeatherChanceOfRainDetail(currentHour, chanceOfRain[2], width)
                }
            }
            height < 250.dp -> {
                Column() {
                    WeatherChanceOfRainDetail(currentHour, chanceOfRain[0], width)
                    currentHour++
                    WeatherChanceOfRainDetail(currentHour, chanceOfRain[1], width)
                    currentHour++
                    WeatherChanceOfRainDetail(currentHour, chanceOfRain[2], width)
                    currentHour++
                    WeatherChanceOfRainDetail(currentHour, chanceOfRain[3], width)
                }
            }
            height < 300.dp -> {
                Column() {
                    WeatherChanceOfRainDetail(currentHour, chanceOfRain[0], width)
                    currentHour++
                    WeatherChanceOfRainDetail(currentHour, chanceOfRain[1], width)
                    currentHour++
                    WeatherChanceOfRainDetail(currentHour, chanceOfRain[2], width)
                    currentHour++
                    WeatherChanceOfRainDetail(currentHour, chanceOfRain[3], width)
                    currentHour++
                    WeatherChanceOfRainDetail(currentHour, chanceOfRain[4], width)
                }
            }
            else -> {
                Column() {
                    WeatherChanceOfRainDetail(currentHour, chanceOfRain[0], width)
                    currentHour++
                    WeatherChanceOfRainDetail(currentHour, chanceOfRain[1], width)
                    currentHour++
                    WeatherChanceOfRainDetail(currentHour, chanceOfRain[2], width)
                    currentHour++
                    WeatherChanceOfRainDetail(currentHour, chanceOfRain[3], width)
                    currentHour++
                    WeatherChanceOfRainDetail(currentHour, chanceOfRain[4], width)
                    currentHour++
                    WeatherChanceOfRainDetail(currentHour, chanceOfRain[5], width)
                }
            }
        }
    }
}

@Composable
fun WeatherChanceOfRainDetail(hour: Int, chanceOfRain: Int, width: Dp) {
    
    val text = if (width < 150.dp) {
        "$hour : ${chanceOfRain}%"
    } else {
        "$hour:00 : ${chanceOfRain}%"
    }
    Row() {
        Text(text = text, style = MaterialTheme.typography.h4, textAlign = TextAlign.Center)
    }
}