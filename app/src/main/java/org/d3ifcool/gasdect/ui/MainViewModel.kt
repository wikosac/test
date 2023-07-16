package org.d3ifcool.gasdect.ui

import android.util.Log
import androidx.lifecycle.*
import org.d3ifcool.gasdect.api.ApiConfig
import org.d3ifcool.gasdect.model.ResponseRiwayat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel() : ViewModel() {

    private val _intValue = MutableLiveData<Int>()
    val intValue: LiveData<Int> = _intValue

    private val _boolValue = MutableLiveData<Boolean>()
    val boolValue: LiveData<Boolean> = _boolValue

    fun getIntValue(token: String) {
        val client = ApiConfig.getApiService().getValue(token, "")
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

    fun isConnected() {
        val client = ApiConfig.getApiService().isConnected("9PWWYxhkSuCnr4OD3VoKrfCPx0WsC4O7")
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
        call.enqueue(object : Callback<ResponseRiwayat> {
            override fun onResponse(call: Call<ResponseRiwayat>, response: Response<ResponseRiwayat>, ) {
                Log.d("RETRO", "response : " + response.body().toString())
                val kode = response.body()?.kode
            }

            override fun onFailure(call: Call<ResponseRiwayat>, t: Throwable) {
                Log.e(MainActivity.TAG, "gagal tambah data riwayat!", t)
            }
        })
    }
}