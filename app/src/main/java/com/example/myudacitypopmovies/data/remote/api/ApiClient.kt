package com.example.myudacitypopmovies.data.remote.api

import com.example.myudacitypopmovies.utils.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private var client: OkHttpClient? = null
    private var sInstance: MovieService? = null
    private val sLock = Any()
    val instance: MovieService?
        get() {
            synchronized(sLock) {
                if (sInstance == null) {
                    sInstance = retrofitInstance.create(MovieService::class.java)
                }
                return sInstance
            }
        }

    private val retrofitInstance: Retrofit
        private get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(client)
                .build()

    init {
        val logging = HttpLoggingInterceptor()
        com.example.myudacitypopmovies.data.remote.api.logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        client = OkHttpClient.Builder()
                .addInterceptor(com.example.myudacitypopmovies.data.remote.api.logging)
                .addInterceptor(AuthInterceptor())
                .build()
    }
}