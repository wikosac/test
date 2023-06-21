package org.d3ifcool.gasdect.ui

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import org.d3ifcool.gasdect.api.ApiConfig
import org.d3ifcool.gasdect.model.ResponseHistori
import org.d3ifcool.gasdect.ui.main.MainFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel() : ViewModel() {

    private val _intValue = MutableLiveData<Int>()
    val intValue: LiveData<Int> = _intValue

    private val _boolValue = MutableLiveData<Boolean>()
    val boolValue: LiveData<Boolean> = _boolValue

    fun getIntValue() {
        val client = ApiConfig.getApiService().getValue("9PWWYxhkSuCnr4OD3VoKrfCPx0WsC4O7", "")
        client.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    _intValue.value = response.body()
                    Log.d("testo", "onResponse: ${_intValue.value}")
                } else {
                    Log.e("testo", "onFailurei: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("testo", "onFailuree: ${t.message.toString()}")
            }
        })
    }

    fun isConnected(token: String) {
        val client = ApiConfig.getApiService().isConnected(token)
        client.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    _boolValue.value = response.body()
                    Log.d("testo", "onResponse: ${_boolValue.value}")
                } else {
                    Log.e("testo", "onFailurei: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.e("testo", "onFailuree: ${t.message.toString()}")
            }
        })
    }

     fun inputHistory(token: String, time: String, gas: String){
        val call = ApiConfig.create().createHistori(token, time, gas)
        call.enqueue(object : Callback<ResponseHistori> {
            override fun onResponse(call: Call<ResponseHistori>, response: Response<ResponseHistori>, ) {
                Log.d("RETRO", "response : " + response.body().toString())
                val kode = response.body()?.kode
            }

            override fun onFailure(call: Call<ResponseHistori>, t: Throwable) {
                Log.e(MainFragment.TAG, "gagal tambah peliharaan!", t)
            }
        })
    }
}