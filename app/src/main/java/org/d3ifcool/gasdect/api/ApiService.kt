package org.d3ifcool.gasdect.api

import org.d3ifcool.gasdect.model.ResponseRiwayat
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
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

    @FormUrlEncoded
    @POST("insertGasDetec.php")
    fun createHistori(
        @Field("token") idtoken: String,
        @Field("time") time: String,
        @Field("gasvalue") gasvalue: String
    ): Call<ResponseRiwayat>

    @GET("getGasDetec.php")
    fun getDataGas(@Query("token") id: String): Call<ResponseRiwayat>
}
