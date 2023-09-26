package com.example.goiapi.API

import com.example.goiapi.data.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIservice {
    @GET("api/")
    fun getData(@Query("latest") latest : Boolean): Call<User>
}