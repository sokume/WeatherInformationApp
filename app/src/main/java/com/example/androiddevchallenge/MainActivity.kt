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
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Speed
import androidx.compose.material.icons.rounded.Umbrella
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
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
    val (showDialog, setShowDialog) =  remember { mutableStateOf(false) }
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "WeatherInformationApp") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                Log.d("TAG","FAB Action")
                setShowDialog(true)
            }) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Date",
                )
                DialogDemo(showDialog, setShowDialog)
            }
        },
        content = {
            Surface(color = MaterialTheme.colors.background) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                    LocationTab(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp),
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
                                .fillMaxSize()
                                .padding(start = 16.dp, bottom = 32.dp, end = 16.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .border(width = 8.dp, color = MaterialTheme.colors.secondary),
                        ) {
                            val weatherMessage = viewModel.weatherMessage
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                item {
                                    Text(
                                        text = weatherMessage,
                                        modifier = Modifier.padding(start = 8.dp,top = 8.dp,end = 8.dp,bottom = 32.dp),
                                        style = MaterialTheme.typography.caption)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
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

    val state = remember { mutableStateOf(0) }
    val titles = viewModel.locationPositions
    ScrollableTabRow(
        selectedTabIndex = state.value,
        modifier = modifier
            .padding(top = 4.dp,bottom = 4.dp),
        edgePadding = 16.dp,
    ) {
        titles.forEachIndexed { index, title ->
            Tab(
                selected = state.value == index,
                onClick = {
                    state.value = index
                    viewModel.changeTab(index)
                }) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.button,
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
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopStart
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.CalendarToday,
                            modifier = Modifier
                                .padding(4.dp)
                                .size(32.dp),
                            contentDescription = "Date",
                            tint = Color.Gray
                        )
                    }
                    WeatherDayView(
                        viewModel.weatherDate,
                        leftTopWidth,
                        leftTopHeight
                    )
                }
                Box(
                    Modifier
                        .width(rightTopWidth)
                        .height(rightTopHeight)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.WbSunny,
                            modifier = Modifier
                                .padding(4.dp)
                                .size(32.dp),
                            contentDescription = "Date",
                            tint = Color.Gray
                        )
                    }
                    WeatherTypeView(
                        viewModel.weatherTypes,
                        rightTopWidth,
                        rightTopHeight
                    )
                }
            }
            Row() {
                Box(
                    Modifier
                        .width(leftBottomWidth)
                        .height(leftBottomHeight)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Speed,
                            modifier = Modifier
                                .padding(4.dp)
                                .size(32.dp),
                            contentDescription = "Date",
                            tint = Color.Gray
                        )
                    }
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
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Umbrella,
                            modifier = Modifier
                                .padding(4.dp)
                                .size(32.dp),
                            contentDescription = "Date",
                            tint = Color.Gray
                        )
                    }

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
            width < 120.dp -> "dd"
            width < 200.dp -> "MM/dd"
            else -> "MM/dd, E"
        }
        val format = SimpleDateFormat(formatPatten, Locale.getDefault())
        val dateString = format.format(weatherDate)
        Text(text = dateString, style = MaterialTheme.typography.h2)
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
        "${hourToString(hour)}:"
    } else {
        "${hourToString(hour)}:00 : "
    }
    Row() {
        Text(text = text, style = MaterialTheme.typography.h2, textAlign = TextAlign.Center)
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
    val text = if (width < 180.dp) {
        "${hourToString(hour)}:${temperature.toInt()}℃"
    } else {
        "${hourToString(hour)}:00 : ${temperature}℃"
    }
    Row() {
        Text(text = text, style = MaterialTheme.typography.h2, textAlign = TextAlign.Center)
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

    val text = if (width < 160.dp) {
        "${hourToString(hour)}:${chanceOfRain}%"
    } else {
        "${hourToString(hour)}:00 : ${chanceOfRain}%"
    }
    Row() {
        Text(text = text, style = MaterialTheme.typography.h2, textAlign = TextAlign.Center)
    }
}

fun hourToString(hour: Int): String {
    val hourInt = if (hour > 23) {
        hour - 24
    } else {
        hour
    }
    return when (hourInt) {
        0, 1, 2, 3, 4, 5,
        6, 7, 8, 9,
        -> "0${hourInt}"
        else -> "${hourInt}"
    }
}


@Composable
fun DialogDemo(showDialog: Boolean, setShowDialog: (Boolean) -> Unit) {
    val textState = remember { mutableStateOf(TextFieldValue()) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text("Add Location")
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.addLocation(textState.value.text)
                        setShowDialog(false)
                    },
                ) {
                    Text("Ok")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        // Change the state to close the dialog
                        setShowDialog(false)
                    },
                ) {
                    Text("Cancel")
                }
            },
            text = {
                Text("Enter the name of the location to be added")
                TextField(
                    value = textState.value,
                    onValueChange = { textState.value = it }
                )
            },
        )
    }
}