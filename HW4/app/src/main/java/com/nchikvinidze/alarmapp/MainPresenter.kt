package com.nchikvinidze.alarmapp

import android.app.PendingIntent
import android.content.Intent
import android.content.SharedPreferences

class MainPresenter(var view: IMainView?, sharedPref : SharedPreferences) : IMainPresenter{
    private val interactor = MainInteractor(this, sharedPref)
    var alarmsList = ArrayList<Pair<String, String>>()

    override fun onAlarmListFetched(alarms: ArrayList<Pair<String, String>>) {
        alarmsList = alarms
        view?.updateRV()
    }

    fun fetchCurAlarmList(){
        interactor.getAlarmsList()
    }

    fun onAlarmAdded(time : String, on : Boolean, id : String){
        interactor.addAlarm(time, on, id)
    }

    fun saveTheme(darkMode : Boolean){
        interactor.saveTheme(darkMode)
    }

    fun determineIfDarkTheme(){
        var isDarkMode = interactor.prevTheme()
        if(isDarkMode){
            view?.darkMode = true
        }
    }

    fun deleteAlarm(id : String){
        interactor.notifyAlarmDeleted(id)
    }

    override fun onAlarmDeleted(id : String){ // unda gaitishos alarmi
        view?.deleteAlarm(id)
    }

    fun getCurAlarmsCount(){
        view?.alarmsCount = alarmsList.size
    }

    override fun onAlarmResumed(id: String, hourOfDay: Int, minute: Int) {
        view?.resumeAlarm(id, hourOfDay, minute)
    }

    fun nextId(): Int{
        return interactor.getMaxId()+1
    }

    override fun alarmUpdated(id: String, on : Boolean) {
        interactor.updateAlarmStatus(id, on)
    }
}