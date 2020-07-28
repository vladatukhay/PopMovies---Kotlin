package com.example.myudacitypopmovies.utils

import android.os.Handler
import android.os.Looper
import com.example.myudacitypopmovies.utils.AppExecutors
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors(private val diskIO: Executor, private val mainThread: Executor, private val networkIO: Executor) {
    fun diskIO(): Executor {
        return diskIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    fun networkIO(): Executor {
        return networkIO
    }

    class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    companion object {
        @Volatile
        private var sInstance: AppExecutors? = null
        private const val THREAD_COUNT = 5
        val instance: AppExecutors?
            get() {
                if (sInstance == null) {
                    synchronized(AppExecutors::class.java) {
                        if (sInstance == null) {
                            sInstance = AppExecutors(Executors.newSingleThreadExecutor(),
                                    Executors.newFixedThreadPool(THREAD_COUNT),
                                    MainThreadExecutor())
                        }
                    }
                }
                return sInstance
            }
    }

}