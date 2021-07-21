package com.nchikvinidze.alarmapp

import android.content.SharedPreferences
// value-shi string: pirveli simbolo agnishnavdes 0 - off, 1 - on, danarcheni iqneba alarm id
class MainInteractor(val presenter: IMainPresenter, sharedPref : SharedPreferences) { // aq shared preferences minda mqondes
    //var alarmsList = ArrayList<Pair<String, Boolean>>()
    var sharedPreferences = sharedPref

    fun addAlarm(time : String, on : Boolean, id : String){

        with (sharedPreferences.edit()) {
            if(on){
                putString(id, "1$time")
            } else {
                putString(id, "0$time")
            }
            apply()
        }
        getAlarmsList()
        //presenter.signalElementAdded()
    }

    fun updateAlarmStatus(id: String, on : Boolean){ // on is not needed here, left for checking purposes
        var elem = sharedPreferences.getString(id, "ERROR")
        elem = if(elem?.get(0)=='0')
            "1" + elem?.substring(1)
        else
            "0"+ elem?.substring(1)

        with (sharedPreferences.edit()) {
            putString(id, elem)
            apply()
        }
    }

    fun getMaxId() : Int{
        var res = 0
        for (elem in sharedPreferences.all) {
            if(elem.key != MainActivity.IS_CUR_THEME_DARK)
                if(Integer.parseInt(elem.key) > res) res = Integer.parseInt(elem.key)
        }
        return res
    }

    fun getAlarmsList(){
        var alarmsList = ArrayList<Pair<String, String>>()
        for (elem in sharedPreferences.all) {
            if(elem.key != MainActivity.IS_CUR_THEME_DARK)
                alarmsList.add(Pair(elem.key as String, elem.value as String))
        }
        presenter.onAlarmListFetched(alarmsList)
    }

    fun saveTheme(darkMode : Boolean){
        with (sharedPreferences.edit()) {
            putBoolean(MainActivity.IS_CUR_THEME_DARK, darkMode)
            apply()
        }
    }

    fun prevTheme() : Boolean{
        return sharedPreferences.getBoolean(MainActivity.IS_CUR_THEME_DARK, false)
    }

    fun notifyAlarmDeleted(id : String){
        with(sharedPreferences.edit()){
            remove(id)
            apply()
        }
        presenter.onAlarmDeleted(id)
    }
}