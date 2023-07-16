package org.d3ifcool.gasdect
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import org.d3ifcool.gasdect.service.ForegroundService
import org.d3ifcool.gasdect.service.FirebaseMessagingService

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        val serviceIntent = Intent(this, ForegroundService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                FirebaseMessagingService.CHANNEL_ID,
                FirebaseMessagingService.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
