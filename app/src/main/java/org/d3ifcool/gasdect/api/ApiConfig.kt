package org.d3ifcool.gasdect.api

import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import xin.sparkle.moshi.NullSafeKotlinJsonAdapterFactory
import xin.sparkle.moshi.NullSafeStandardJsonAdapters

class ApiConfig {
    companion object {
        private const val BASE_URL = "https://api.luckytruedev.com/gasdetec/"
        // API Data Gas
        fun create(): ApiConfig {
            val moshi = Moshi.Builder()
                .add(NullSafeStandardJsonAdapters.FACTORY)
                .add(NullSafeKotlinJsonAdapterFactory())
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
            return retrofit.create(ApiConfig::class.java)
        }

        // API NodeMCU
        fun getApiService(): ApiService {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val authInterceptor = Interceptor { chain ->
                val req = chain.request()
                val reqHeaders = req.newBuilder()
                    .build()
                chain.proceed(reqHeaders)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://sgp1.blynk.cloud/external/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
