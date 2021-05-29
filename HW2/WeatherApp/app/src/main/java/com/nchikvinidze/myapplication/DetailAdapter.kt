package com.nchikvinidze.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DetailAdapter(var list: ArrayList<HourDetails>) : RecyclerView.Adapter<HourDetailViewHolder>()  {

    lateinit var passedContext: Context
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HourDetailViewHolder, position: Int) {
        var item = list[position]
        holder.date.text = item.date
        var weatherIconUrl = "https://openweathermap.org/img/wn/" +
                item.icon + "@2x.png"
        Glide.with(passedContext).load(weatherIconUrl).into(holder.iconImg)
        holder.temp.text = item.temp.toString()+"°"
        holder.desc.text = item.desc
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourDetailViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.hour_list_item, parent, false)
        passedContext = parent.context
        return HourDetailViewHolder(view)
    }
}

class HourDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var date = itemView.findViewById<TextView>(R.id.hourDate)
    var iconImg = itemView.findViewById<ImageView>(R.id.hourIcon)
    var temp = itemView.findViewById<TextView>(R.id.hourTemperature)
    var desc = itemView.findViewById<TextView>(R.id.hourDescription)
}

data class HourDetails(var date: String, var icon: String, var temp: Int, var desc: String)