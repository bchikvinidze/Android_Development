package com.nchikvinidze.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlin.math.roundToInt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DayFragment : Fragment() {
    lateinit var weatherIcon: ImageView
    lateinit var temperature: TextView
    lateinit var weatherStatus: TextView
    lateinit var temperaturedynamic: TextView
    lateinit var feelslikedynamic: TextView
    lateinit var humiditydynamic: TextView
    lateinit var pressuredynamic: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_day, container, false)
        weatherIcon = view.findViewById<ImageButton>(R.id.weatherIcon)
        temperature = view.findViewById<TextView>(R.id.temperature)
        weatherStatus = view.findViewById<TextView>(R.id.weatherStatus)
        temperaturedynamic = view.findViewById<TextView>(R.id.temperaturedynamic)
        feelslikedynamic = view.findViewById<TextView>(R.id.feelslikedynamic)
        humiditydynamic = view.findViewById<TextView>(R.id.humiditydynamic)
        pressuredynamic = view.findViewById<TextView>(R.id.pressuredynamic)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun updateData(data: CommentModel?){
        var weatherIconUrl = "https://openweathermap.org/img/wn/" +
                data?.weather?.get(0)?.icon + "@2x.png"
        Glide.with(this).load(weatherIconUrl).into(weatherIcon)
        temperature.setText(data?.main?.temp?.roundToInt().toString()+"°")
        weatherStatus.setText(data?.weather?.get(0)?.description?.toUpperCase())
        temperaturedynamic.setText(data?.main?.temp.toString()+"°")
        feelslikedynamic.setText(data?.main?.feels_like?.roundToInt().toString())
        humiditydynamic.setText(data?.main?.humidity?.roundToInt().toString())
        pressuredynamic.setText(data?.main?.pressure?.roundToInt().toString())
    }

}