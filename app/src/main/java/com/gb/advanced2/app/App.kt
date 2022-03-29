package com.gb.advanced2.app

import android.app.Application
import com.gb.advanced2.app.di.DaggerAppComponent

class App : Application() {
    val appComponent by lazy {
        DaggerAppComponent.builder().build()
    }

    init {
        instance = this
    }

    companion object {
        var instance: App? = null
    }
}