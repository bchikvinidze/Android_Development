package com.nchikvinidze.myapplication

import org.json.JSONArray
import org.json.JSONObject
import java.math.BigInteger

// used only in daily JSON
data class CoordModel(val lon: Float, val lat: Float)
data class WeatherModel(val id: Int, val main: String, val description: String, val icon: String)
data class MainModel(val temp: Float, val feels_like: Float, val temp_min: Float, val temp_max: Float,
                     val pressure: Float, val humidity: Float)
data class WindModel(val speed: Float, val deg: Float)
data class CloudsModel(val all: Float)
data class Sys(val type: Float, val id: Int, val country: String, val sunrise: String, val sunset: String)
data class CommentModel(val coord: CoordModel, val weather: List<WeatherModel>, val base: String, val main: MainModel,
                        val visibility: Int, val wind: WindModel, val clouds: CloudsModel, val dt: String,
                        val sys: Sys, val timezone: Int, val id: Int, val name: String, val cod: Int)

// used in hourly JSON
data class CityModel(val id: Int, val name: String, val coord: HourCoordModel)
data class HourDetail(val dt: String, val main: HourMainModel, val weather: List<WeatherModel>, val clouds: Placeholder,
                      val wind: HourWindModel, val visibility: Int, val pop: Float, val sys: HourSysModel, val dt_txt: String)
data class HourMainModel(val temp: Float, val feels_like: Float, val temp_min: Float, val temp_max: Float, val pressure: Float,
                        val sea_level: Int, val grnd_level: Int, val humidity: Float, val temp_kf: Float)
data class Placeholder(val all: Int)
data class HourWindModel(val speed: Float, val deg: Float, val gust: Float)
data class HourSysModel(val pod: String)
data class HourCoordModel(val country: String, val population: Int, val timezone: Int, val sunrise: String, val sunset: String)
data class HourCommentModel(val cod: String, val message: Float, val cnt: Int, val list: List<HourDetail>,
                        val city: CityModel)