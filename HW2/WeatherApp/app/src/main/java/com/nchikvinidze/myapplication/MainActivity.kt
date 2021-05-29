package com.nchikvinidze.myapplication

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var iconDaily : ImageButton
    lateinit var iconHourly: ImageButton
    lateinit var imageGeorgia: ImageButton
    lateinit var imageUK: ImageButton
    lateinit var imageJamaica: ImageButton
    lateinit var scrollView: ScrollView
    lateinit var capitalName: TextView
    val key = "3a04cf55f9e6dd710087572c7e7f0c0b"
    lateinit var context : MainActivity
    lateinit var viewpager: ViewPager2
    lateinit var dayFragment: DayFragment
    lateinit var hourFragment: HourFragment
    lateinit var outmost: ConstraintLayout
    var hourData: HourCommentModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        iconDaily = findViewById<ImageButton>(R.id.imageToday)
        iconHourly = findViewById<ImageButton>(R.id.imageHourly)
        imageGeorgia = findViewById<ImageButton>(R.id.imageGeorgia)
        imageUK = findViewById<ImageButton>(R.id.imageUK)
        imageJamaica = findViewById<ImageButton>(R.id.imageJamaica)
        scrollView = findViewById<ScrollView>(R.id.scrollView2)
        capitalName = findViewById<TextView>(R.id.capitalName)
        viewpager = findViewById(R.id.viewPager)
        outmost = findViewById<ConstraintLayout>(R.id.outmost)
        dayFragment = DayFragment()
        hourFragment = HourFragment()
        viewpager.adapter = ViewPagerAdapter(this, dayFragment, hourFragment)
        context = this

        //retrofit setup
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var weatherAPI = retrofit.create(WeatherAPI::class.java)
        var weatherDetailAPI = retrofit.create(WeatherDetailAPI::class.java)

        display("tbilisi", weatherAPI, weatherDetailAPI)
        setupListeners(weatherAPI, weatherDetailAPI)

    }

    fun display(city : String, weatherAPI: WeatherAPI, weatherDetailAPI: WeatherDetailAPI){
        capitalName.setText(city.toUpperCase())

        weatherAPI.getComment(city, key, "metric").enqueue(object : Callback<CommentModel>{
            override fun onFailure(call: Call<CommentModel>, t: Throwable) {
                Log.d("logdata", t.toString())
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<CommentModel>, response: Response<CommentModel>) {
                if(response.isSuccessful) {
                    Log.d("logdata", response.body().toString())
                    val data = response.body()
                    setbackground(data)
                    dayFragment.updateData(data)
                }
            }
        })

        weatherDetailAPI.getComment(city, key, "metric").enqueue(object : Callback<HourCommentModel>{
            override fun onFailure(call: Call<HourCommentModel>, t: Throwable) {
                Log.d("logdata", t.toString())
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<HourCommentModel>, response: Response<HourCommentModel>) {
                if(response.isSuccessful && hourFragment.view != null) {
                    Log.d("logdata", response.body().toString())
                    val data = response.body()
                    hourFragment.updateData(data)
                } else if (response.isSuccessful){
                    hourData = response.body()
                }
            }
        })
    }

    fun setbackground(data: CommentModel?){
        val tzh = (data?.timezone ?: 0) /3600
        var calendar = Calendar.getInstance()
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"))
        calendar.setTimeInMillis(System.currentTimeMillis())
        var hours = calendar.get(Calendar.HOUR_OF_DAY) + tzh
        var day = false
        if(hours in 6..17) {
            day = true
            outmost.setBackgroundColor(Color.parseColor("#059FE4"))
        } else {
            outmost.setBackgroundColor(Color.parseColor("#240050"))
        }
    }

    fun setupListeners(weatherAPI: WeatherAPI, weatherDetailAPI: WeatherDetailAPI){
        imageGeorgia?.setOnClickListener {
            display("tbilisi", weatherAPI, weatherDetailAPI)
        }
        imageUK?.setOnClickListener {
            display("london", weatherAPI, weatherDetailAPI)
        }
        imageJamaica?.setOnClickListener {
            display("kingston", weatherAPI, weatherDetailAPI)
        }
        iconDaily?.setOnClickListener{
            viewpager.setCurrentItem(0)
        }
        iconHourly?.setOnClickListener{
            viewpager.setCurrentItem(1)
        }
    }

    override fun onDestroy() {
        super.onDestroy() // Add this line
    }
}