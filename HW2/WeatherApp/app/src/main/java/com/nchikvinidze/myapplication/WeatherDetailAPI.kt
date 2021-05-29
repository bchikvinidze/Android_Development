package com.nchikvinidze.myapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherDetailAPI {
    @GET("/data/2.5/forecast")
    //fun getPosts(): Call<List<PostModel>>
    fun getComment(@Query("q") city: String,
                   @Query("appid") key: String,
                   @Query("units") unit: String): Call<HourCommentModel>
}