package org.d3ifcool.gasdect.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("get")
    fun getValue(
        @Query("token") token: String,
        @Query("v1") v1: String
    ): Call<Int>

    @GET("isHardwareConnected")
    fun isConnected(
        @Query("token") token: String
    ) : Call<Boolean>
}

interface ApiService2 {
    @GET("sendNotification")
    fun sendNotification(): Call<String>
}
