package com.nchikvinidze.myapplication

import okhttp3.RequestBody
import org.w3c.dom.Comment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("/data/2.5/weather")
    //fun getPosts(): Call<List<PostModel>>
    fun getComment(@Query("q") city: String,
                   @Query("appid") key: String,
                   @Query("units") unit: String): Call<CommentModel>
}