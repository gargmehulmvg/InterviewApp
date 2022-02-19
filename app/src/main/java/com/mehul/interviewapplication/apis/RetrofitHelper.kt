package com.mehul.interviewapplication.apis

import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {

    private const val BASE_URL = "https://quotable.io/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            client(getHttpClient())
        }.build()
    }


    private fun getHttpClient(): OkHttpClient {
        val loggingInterface = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder().apply {
            connectTimeout(1, TimeUnit.MINUTES)
            callTimeout(1, TimeUnit.MINUTES)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            addInterceptor(loggingInterface)
            protocols(arrayListOf(Protocol.HTTP_1_1, Protocol.HTTP_2))
        }.build()
    }

}