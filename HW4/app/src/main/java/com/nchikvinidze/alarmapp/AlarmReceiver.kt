package com.nchikvinidze.alarmapp

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import ge.ezarkua.notificationsdemo.NotificationDetailsActivity
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onReceive(context: Context?, intent: Intent?) {
        //Log.d(TAG, "message received")
        context?.let {
            var notificationManager = NotificationManagerCompat.from(context)
            if (intent?.action == ALARM_ACTION_NAME) {

                val notificationClickPendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    Intent(context, MainActivity::class.java),
                    0
                )

                val buttonCancelIntent = PendingIntent.getBroadcast(
                    context,
                    0,
                    Intent(NOTIFICATION_CANCEL).apply {
                        `package` = context.packageName
                    },
                    0
                )

                val receiverButtonClick = PendingIntent.getBroadcast(
                    context,
                    0,
                    Intent("com.nchikvinidze.alarmapp.NOTIFICATION_CLICK").apply {
                        `package` = context.packageName
                    },
                    PendingIntent.FLAG_CANCEL_CURRENT
                )

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    createChannel(notificationManager)
                }

                var calendarNow = Calendar.getInstance()
                var curHR = calendarNow.get(Calendar.HOUR_OF_DAY).toString()
                var curMN = calendarNow.get(Calendar.MINUTE).toString()
                if(curHR.length==1) curHR = "0" + curHR
                if(curMN.length==1) curMN = "0" + curMN
                var timeNow = curHR+":"+curMN

                var notification = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.alarm_clock)
                    .setContentTitle("Alarm message!")
                    .setContentText("Alarm set on $timeNow")
                    .setContentIntent(notificationClickPendingIntent)
                    .addAction(R.mipmap.ic_launcher, "CANCEL", buttonCancelIntent)
                    .addAction(R.mipmap.ic_launcher, "SNOOZE", receiverButtonClick)
                    .build()

                notificationManager.notify(NOTIFICATION_ID, notification)
            }else if(intent?.action == NOTIFICATION_CANCEL){ //cancelze aq wesit
                notificationManager.cancel(NOTIFICATION_ID)
            } else { // snoozes dacheris dros
                notificationManager.cancel(NOTIFICATION_ID)

                var pi = PendingIntent.getBroadcast(
                    context,
                    MainActivity.ALARM_SNOOZE_CODE,
                    Intent(ALARM_ACTION_NAME).apply {
                        `package` = context.packageName
                    },
                    0
                )
                var alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 60 * 1000, pi)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(notificationManager: NotificationManagerCompat) {
        var notificationChannel = NotificationChannel(CHANNEL_ID, "channel_name", IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(notificationChannel)
    }

    companion object {
        const val TAG = "ALARM_REVIECER"
        const val ALARM_ACTION_NAME = "com.nchikvinidze.alarmapp.ALARM_ACTION"
        const val NOTIFICATION_ID = 22
        const val CHANNEL_ID = "com.nchikvinidze.alarmapp.CHANNEL_1"
        const val NOTIFICATION_CANCEL = "com.nchikvinidze.alarmapp.NOTIFICATION_CANCEL"
    }
}