package com.example.myudacitypopmovies.data.remote.api

import com.example.myudacitypopmovies.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

//Interceptor used to add TheMovieDB API Key to the http request
class AuthInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}