package com.nchikvinidze.alarmapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nchikvinidze.alarmapp.AlarmReceiver
import java.util.*

class MainActivity() : AppCompatActivity(), TimePickerDialog.OnTimeSetListener, IMainView {
    lateinit var switchTheme : TextView
    lateinit var addButton : ImageView
    lateinit var rv : RecyclerView
    lateinit var rvAdapter : AlarmRVAdapter
    lateinit var presenter : MainPresenter
    lateinit var sharedPref : SharedPreferences
    lateinit var mainLayout : ConstraintLayout
    lateinit var toolbar : View
    override var darkMode = false
    lateinit var alarmManager : AlarmManager
    override var alarmsCount = 0

    companion object {
        val LIGHT_BACKGROUND = "#f9f9f9"
        val IS_CUR_THEME_DARK = "CUR_THEME"

        const val JOB_ID = 100
        const val ALARM_SNOOZE_CODE = 200
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.saveTheme(darkMode)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById<View>(R.id.toolbar)
        switchTheme = findViewById<TextView>(R.id.switchTheme)
        addButton =  findViewById<ImageView>(R.id.addIcon)
        rv = findViewById<RecyclerView>(R.id.rv)
        sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        presenter = MainPresenter(this, sharedPref)
        rvAdapter =  AlarmRVAdapter(presenter, false)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mainLayout = findViewById<ConstraintLayout>(R.id.main_layout)
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        switchTheme.setOnClickListener {
            darkMode = !darkMode
            changeTheme()
        }

        addButton.setOnClickListener {
            var calendar = Calendar.getInstance()

            var timePickerDialog = TimePickerDialog(
                this,
                this,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
            )
            timePickerDialog.show()
        }

        presenter.determineIfDarkTheme()
        presenter.fetchCurAlarmList()
        updateRV()
        presenter.getCurAlarmsCount()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        //ლისტში ჩამატება - რესაქილერვიუში da sharedpreferences-shi
        var time = hourOfDay.toString() + ":"
        if(hourOfDay <= 9) time = "0"+time
        if(minute < 10) time += "0"
        time += minute.toString()

        timeSet(time, hourOfDay, minute)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun timeSet(time : String, hourOfDay: Int, minute: Int){
        var calendarNow = Calendar.getInstance()
        var curHR = calendarNow.get(Calendar.HOUR_OF_DAY).toString()
        var curMN = calendarNow.get(Calendar.MINUTE).toString()
        if(curHR.length==1) curHR = "0" + curHR
        if(curMN.length==1) curMN = "0" + curMN
        var timeNow = curHR+":"+curMN
        if(timeNow < time){ // mimdinare dgis momavali tua
            var id = presenter.nextId()
            presenter.onAlarmAdded(time, true, id.toString())
            startAlarm(hourOfDay, minute, id)
        }
    }

    override fun updateRV(){
        rvAdapter.list.clear()
        rvAdapter.notifyDataSetChanged()
        for (elem in presenter.alarmsList){
            rvAdapter.list.add(elem)
            rvAdapter.notifyDataSetChanged()
        }
    }

    override fun changeTheme(){
        rvAdapter.list.clear()
        rvAdapter.notifyDataSetChanged()
        if(darkMode){
            switchTheme.text = "Switch to light"
            mainLayout.setBackgroundColor(Color.BLACK)
            toolbar.setBackgroundColor(Color.GRAY)
        } else {
            mainLayout.setBackgroundColor(Color.parseColor(LIGHT_BACKGROUND))
            switchTheme.text = "Switch to dark"
            toolbar.setBackgroundColor(Color.WHITE)
        }
        rvAdapter =  AlarmRVAdapter(presenter, darkMode)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        updateRV()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun startAlarm(hourOfDay: Int, minute: Int, id: Int) {
        var pi = PendingIntent.getBroadcast(
            this,
            id,
            Intent(this, AlarmReceiver::class.java),
            0//PendingIntent.FLAG_CANCEL_CURRENT
        )

        var calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        // esaa zogierti SAMSUNG-ebis problema (chemi dzveli samsungia).
        // mgoni chemi sistemis dro aris areuli da amitom momiwia 1-s dakleba.
        // issue-s shesaxeb ixilet aq:
        // https://stackoverflow.com/questions/34074955/android-exact-alarm-is-always-3-minutes-off
        calendar.set(Calendar.MINUTE, minute-1);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pi)
    }

    override fun deleteAlarm(id: String) {
        var alarmid = Integer.parseInt(id)
        var pendingIntent = PendingIntent.getBroadcast(
            this,
            alarmid,
            Intent(this, AlarmReceiver::class.java),
            0)
        alarmManager.cancel(pendingIntent)
    }

    override fun resumeAlarm(id: String, hourOfDay: Int, minute: Int) {
        var pi = PendingIntent.getBroadcast(
            this,
            Integer.parseInt(id),
            Intent(this, AlarmReceiver::class.java).apply {
                //`package` = packageName
            },
            0
        )

        var calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pi)
    }
}
