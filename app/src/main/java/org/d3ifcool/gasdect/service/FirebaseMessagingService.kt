package org.d3ifcool.gasdect.service

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.d3ifcool.gasdect.R

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle incoming FCM messages and display notifications here.
        showNotification(remoteMessage)
        Log.d("testo", "onMessageReceived: $remoteMessage")
    }

    override fun onNewToken(token: String) {
        // If you need to handle token refresh, implement it here.
        // You can send the refreshed token to your server or update it locally.
    }

    private fun showNotification(remoteMessage: RemoteMessage) {
        // Create a notification builder
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle(remoteMessage.notification?.title)
            .setContentText(remoteMessage.notification?.body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        // Add any other desired notification settings

        // Display the notification
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }

    companion object {
        const val CHANNEL_ID = "channel_id"
        const val NOTIFICATION_ID = 1
        const val CHANNEL_NAME = "Channel Name"
    }
}
