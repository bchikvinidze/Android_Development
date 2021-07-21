package com.nchikvinidze.alarmapp

import android.app.AlarmManager

interface IMainView {
    var darkMode : Boolean
    var alarmsCount : Int
    //var alarmManager : AlarmManager
    fun changeTheme()
    fun updateRV()
    fun deleteAlarm(id: String)
    fun resumeAlarm(id: String, hourOfDay: Int, minute: Int)
    //fun newItemDisplay()
}