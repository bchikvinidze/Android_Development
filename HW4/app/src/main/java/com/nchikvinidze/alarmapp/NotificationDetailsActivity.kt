package ge.ezarkua.notificationsdemo

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat


class NotificationDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_notification_details)

        var notificationManager = NotificationManagerCompat.from(this)
        notificationManager.cancelAll()
    }
}