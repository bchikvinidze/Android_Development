package com.nchikvinidze.alarmapp

interface IMainPresenter {
    abstract fun onAlarmListFetched(alarms: ArrayList<Pair<String, String> >)
    abstract fun onAlarmDeleted(id: String)
    abstract fun onAlarmResumed(id: String, hourOfDay: Int, minute: Int)
    abstract fun alarmUpdated(id: String, on: Boolean)
    //abstract fun signalElementAdded()
}