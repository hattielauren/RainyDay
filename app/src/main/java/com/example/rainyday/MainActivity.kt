package com.example.rainyday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import java.util.Locale
import androidx.compose.ui.tooling.preview.Preview
import com.example.rainyday.ui.theme.RainyDayTheme
import com.example.rainyday.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Scaffold(
                    topBar = {
                        CustomTopBar()
                    },
                    content = { innerPadding ->
                        MainContent(modifier = Modifier.padding(innerPadding), weatherViewModel = weatherViewModel)
                    }
                )
            }
        }
    }
}

@Composable
fun CustomTopBar(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = stringResource(R.string.rainy_day_title),
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )
    }
}
@Composable
fun MainContent(modifier: Modifier = Modifier, weatherViewModel: WeatherViewModel) {
    val weatherData = weatherViewModel.weatherData.observeAsState(null)

    LaunchedEffect(Unit) {
        weatherViewModel.getCurrentWeather()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = stringResource(R.string.city_name),
                style = TextStyle(fontSize = 25.sp),
                color = Color.Black,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            weatherData.value?.let { weather ->
                val tempInFahrenheit = kelvinToFahrenheit(weather.main.temp)
                val feelsLikeInFahrenheit = kelvinToFahrenheit(weather.main.feelsLike)
                val tempMinInFahrenheit = kelvinToFahrenheit(weather.main.tempMin)
                val tempMaxInFahrenheit = kelvinToFahrenheit(weather.main.tempMax)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.temperature, tempInFahrenheit),
                            style = TextStyle(fontSize = 65.sp),
                            color = Color.Black
                        )

                        Text(
                            text = stringResource(R.string.feels_like, feelsLikeInFahrenheit),
                            style = TextStyle(fontSize = 16.sp),
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }
                    Spacer(modifier = Modifier.width(110.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.sunny),
                        contentDescription = "Sunny Icon",
                        modifier = Modifier.size(100.dp),
                        tint = Color.Unspecified
                    )
                }

                Spacer(modifier = Modifier.height(35.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = stringResource(R.string.low_temperature, tempMinInFahrenheit), style = TextStyle(fontSize = 20.sp))
                        Text(text = stringResource(R.string.high_temperature, tempMaxInFahrenheit), style = TextStyle(fontSize = 20.sp))
                        Text(text = stringResource(R.string.humidity, weather.main.humidity), style = TextStyle(fontSize = 20.sp))
                        Text(text = stringResource(R.string.pressure, weather.main.pressure), style = TextStyle(fontSize = 20.sp))
                    }
                }
            } ?: run {
                Text(text = stringResource(R.string.loading_weather_data), style = TextStyle(fontSize = 20.sp))
            }
        }
    }
}

fun kelvinToFahrenheit(kelvin: Double): Double {
    val fahrenheit = (kelvin - 273.15) * 9 / 5 + 32
    return String.format(Locale.US, "%.2f", fahrenheit).toDouble()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RainyDayTheme {
        MainContent(weatherViewModel = WeatherViewModel())
    }
}