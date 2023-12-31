package com.example.goiapi.API

import android.provider.SyncStateContract
import com.example.goiapi.Constants.Constants.RESULT
import com.example.goiapi.Constants.Constants.URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIClient {
    private lateinit var retrofit: Retrofit

    private val requestInterceptor = Interceptor { chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("results", RESULT)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return@Interceptor chain.proceed(request)
    }


    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    fun getClient(): Retrofit {
        retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
}