package com.gb.advanced2.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.getKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree());
        startKoin {
            androidContext(this@App)
            modules(koinModule)
        }
    }
}