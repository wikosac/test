package org.d3ifcool.gasdect.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import org.d3ifcool.gasdect.R
import org.d3ifcool.gasdect.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Timer
import java.util.TimerTask

class ForegroundService : Service() {

    private val timer = Timer()
    var value: String = ""

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Schedule the periodic timer with the desired interval
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                // Perform the API request at the specified interval
                getCloudFunctions()
            }
        }, 0, 10000) // Replace 10000 with the desired interval in milliseconds

        // Start the service in the foreground
        startForegroundService()

        // Return START_STICKY to indicate that the service should be restarted if it gets terminated
        return START_STICKY
    }

    private fun getCloudFunctions() {
        // Make an API request to check if conditions are met
        val client = ApiConfig.getCloudFunctions().sendNotification()
        client.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    value = response.body()!!
                    Log.d("testo", "onResponse: $value")
                } else {
                    Log.e("testo", "onFailurei: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("testo", "onFailuree: ${t.message.toString()}")
            }
        })
    }

    private fun startForegroundService() {
        // Set up the foreground service notification and start the service
        val notification = createNotification() // Create a notification for the foreground service
        startForeground(FirebaseMessagingService.NOTIFICATION_ID, notification)
    }

    private fun createNotification(): Notification {
        // Create a notification channel (required for Android Oreo and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = FirebaseMessagingService.CHANNEL_ID
            val channelName = FirebaseMessagingService.CHANNEL_NAME
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)

            // Customize additional channel settings if needed

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Build the notification using NotificationCompat.Builder
        val builder = NotificationCompat.Builder(this, FirebaseMessagingService.CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText("Running...")
            .setSmallIcon(R.drawable.baseline_notifications_24)

        // Customize additional notification settings if needed

        return builder.build()
    }

    override fun onBind(intent: Intent?): IBinder? {
        // No binding is needed for this example
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancel the periodic timer when the service is destroyed
        timer.cancel()
    }
}
