package com.nchikvinidze.alarmapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class AlarmRVAdapter(var presenter: MainPresenter, isDarkMode : Boolean) : RecyclerView.Adapter<AlarmItemViewHolder>()  {
    var list = ArrayList<Pair<String, String>>()
    var mode = isDarkMode

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmItemViewHolder {
        if(!mode) // if not dark mode
            return AlarmItemViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.one_alarm, parent, false))
        else
            return AlarmItemViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.one_alarm_night, parent,false))
    }

    override fun onBindViewHolder(holder: AlarmItemViewHolder, position: Int) {
        var isOn = true
        if(list[position].second.elementAt(0) == '0') isOn = false
        var time = list[position].second.substring(1)
        var id = list[position].first
        holder.bindAlarm(time, isOn)

        holder.item.setOnLongClickListener {
            var dialog = AlertDialog.Builder(holder.passedContext.context)
                .setMessage("Are you sure you want to delete item?")
                .setCancelable(false)
                .setPositiveButton(
                    "YES"
                ) { dialog, which ->
                    list.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, list.size)
                    presenter.deleteAlarm(id)
                    dialog?.dismiss()
                }
                .setNegativeButton(
                    "NO"
                ) { dialog, which -> dialog?.dismiss() }
                .create()

            dialog.show()
            return@setOnLongClickListener true
        }

        holder.switch_button.setOnCheckedChangeListener { buttonView, isChecked ->
            //sharedpreferences-shi shecvla
            presenter.alarmUpdated(id, isChecked)
            if(!isChecked) { // stop alarm
                presenter.onAlarmDeleted(id) // droebit deleted
            } else { // resume alarm
                var hour = if(time[0] == '0') {
                    Integer.parseInt(time.substring(1,2))
                } else {
                    Integer.parseInt(time.substring(0,2))
                }

                var minute = if(time[3] == '0') {
                    Integer.parseInt(time.substring(4,5))
                } else {
                    Integer.parseInt(time.substring(3,5))
                }
                presenter.onAlarmResumed(id, hour, minute)
            }
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class AlarmItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var time = itemView.findViewById<TextView>(R.id.time)
    var switch_button = itemView.findViewById<SwitchCompat>(R.id.switch_bar)
    var item = itemView.findViewById<ConstraintLayout>(R.id.alarm_elem)
    var passedContext = itemView

    fun bindAlarm(timeText : String, on : Boolean){
        time.setText(timeText)
        switch_button.isChecked = on
    }
}