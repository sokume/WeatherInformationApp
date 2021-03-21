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

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.google.gson.Gson

private val viewModel = WeatherInformationViewModel()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        if (loadDates.isEmpty()){
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
            LocationText(
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
                                .background(Color.LightGray)
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
fun LocationText(
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
                Text(text = title,style = MaterialTheme.typography.h4,modifier = Modifier.padding(start = 4.dp,end = 4.dp))
            }
        }
    }

    // val locationName = viewModel.currentLocationName
    // Text(
    //     text = locationName,
    //     modifier = modifier,
    //     style = MaterialTheme.typography.h3
    // )
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
                        .background(Color.Red)
                ) {
                }
                Box(
                    Modifier
                        .width(rightTopWidth)
                        .height(rightTopHeight)
                        .background(Color.Yellow)
                ) {
                }
            }
            Row() {

                Box(
                    Modifier
                        .width(leftBottomWidth)
                        .height(leftBottomHeight)
                        .background(Color.Blue)
                ) {
                }
                Box(
                    Modifier
                        .width(rightBottomWidth)
                        .height(rightBottomHeight)
                        .background(Color.Green)
                ) {
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
            .background(Color.Cyan)
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
            .background(Color.Cyan)
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

