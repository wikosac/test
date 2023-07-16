package org.d3ifcool.gasdect.ui

import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import org.d3ifcool.gasdect.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel() : ViewModel() {

    private val _intValue = MutableLiveData<Int>()
    val intValue: LiveData<Int> = _intValue

    private val _boolValue = MutableLiveData<Boolean>()
    val boolValue: LiveData<Boolean> = _boolValue

    init {
        getIntValue()
        isConnected()
        checkToken()
    }

    private fun getIntValue() {
        val client = ApiConfig.getApiService().getValue("9PWWYxhkSuCnr4OD3VoKrfCPx0WsC4O7", "")
        client.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    _intValue.value = response.body()
                    Log.d("testo", "onResponse value: ${_intValue.value}")
                } else {
                    Log.e("testo", "onFailurei: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("testo", "onFailuree: ${t.message.toString()}")
            }
        })
    }

    private fun isConnected() {
        val client = ApiConfig.getApiService().isConnected("9PWWYxhkSuCnr4OD3VoKrfCPx0WsC4O7")
        client.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    _boolValue.value = response.body()
                    Log.d("testo", "onResponse isConnect: ${_boolValue.value}")
                } else {
                    Log.e("testo", "onFailurei: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.e("testo", "onFailuree: ${t.message.toString()}")
            }
        })
    }

    private fun checkToken() {
        val tokenTask = FirebaseMessaging.getInstance().token
        tokenTask.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "FCM token failed.", task.exception)
                return@OnCompleteListener
            }
            Log.d("FCM", "Token: ${task.result}")
        })
    }

    private fun subs() {
        FirebaseMessaging.getInstance().subscribeToTopic("api_response_value")
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    // Subscription successful
//                    // Handle success
//                } else {
//                    // Subscription failed
//                    // Handle failure
//                }
//            }
    }
}