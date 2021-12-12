package com.example.hw6architecture.data.network

import com.example.hw6architecture.immutable_values.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NetworkBuilder {

    fun retrofitInit(): ImdbApiClient {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ImdbApiKeyInterceptor())
            .addInterceptor(httpLoggingInterceptor)
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ImdbApiClient::class.java)
    }
}

