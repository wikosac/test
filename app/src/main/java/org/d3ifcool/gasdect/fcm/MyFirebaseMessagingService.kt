package org.d3ifcool.gasdect.fcm

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.d3ifcool.gasdect.R
import org.d3ifcool.gasdect.api.ApiConfig
import org.d3ifcool.gasdect.ui.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFirebaseMessagingService : FirebaseMessagingService() {

    var value: Int = 0

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle incoming FCM messages and display notifications here.
        handleNotification(remoteMessage)
        Log.d("testo", "onMessageReceived: $remoteMessage")
    }

    override fun onNewToken(token: String) {
        // If you need to handle token refresh, implement it here.
        // You can send the refreshed token to your server or update it locally.
    }

    private fun handleNotification(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body

        getApiValue()
//        if (value > 400)
        showNotification(remoteMessage)
    }

    private fun getApiValue() {
        Log.d("testo", "getApiValue: ")
        // Make an API request to check if conditions are met
        val client = ApiConfig.getApiService().getValue("9PWWYxhkSuCnr4OD3VoKrfCPx0WsC4O7", "")
        client.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    value = response.body()!!
                    Log.d("testo", "onResponse: $value")
                } else {
                    Log.e("testo", "onFailurei: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("testo", "onFailuree: ${t.message.toString()}")
            }
        })
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
    }
}
