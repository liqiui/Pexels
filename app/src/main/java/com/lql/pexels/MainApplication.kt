package com.lql.pexels

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}