package com.example.popularmovies

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class PopularMoviesApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}