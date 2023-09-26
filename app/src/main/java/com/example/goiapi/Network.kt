package com.example.goiapi

import com.example.goiapi.API.APIservice
import com.example.goiapi.Constants.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class Network {
    @Provides
    @Singleton
    internal fun provideInterceptor() : Interceptor {
        val requestInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("results", Constants.RESULT)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
        }
        return requestInterceptor
    }
    @Provides
    @Singleton
    internal fun provideOkHttpClient(interceptor: Interceptor) : OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
        return okHttpClient
    }
    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        var retrofit = Retrofit.Builder()
            .baseUrl(Constants.URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
    @Provides
    @Singleton
    internal fun provideUserAPI(retrofit: Retrofit) : APIservice {
        return retrofit.create(APIservice::class.java)
    }
}