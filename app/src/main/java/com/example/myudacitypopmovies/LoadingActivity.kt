package com.example.myudacitypopmovies

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.bumptech.glide.Glide

class LoadingActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        val imageView = findViewById<ImageView>(R.id.image_loading)
        Glide.with(baseContext).load(R.raw.marvel).into(imageView)
        startLoading()
    }

    private fun startLoading() {
        val handler = Handler()
        handler.postDelayed({ finish() }, 4600)
    }
}