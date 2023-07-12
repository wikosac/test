package org.d3ifcool.gasdect
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import org.d3ifcool.gasdect.fcm.MyFirebaseMessagingService

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        Log.d("testo", "onCreate: MyApp")
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                MyFirebaseMessagingService.CHANNEL_ID,
                "Channel Name",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
