package com.example.hw6architecture.data.network

import com.example.hw6architecture.immutable_values.Constants
import okhttp3.Interceptor
import okhttp3.Response

class ImdbApiKeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        var url = request.url

        url = url.newBuilder()
            .addQueryParameter("api_key", Constants.API_KEY)
            .build()

        request = request.newBuilder().url(url).build()

        return chain.proceed(request)
    }
}