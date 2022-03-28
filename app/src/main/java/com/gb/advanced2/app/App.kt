package com.gb.advanced2.app

import android.app.Application
import com.gb.advanced2.externals.repo.RemoteRepository

class App : Application() {
    val model: Contract.Model = RemoteRepository()

    init { instance = this }

    companion object {
        var instance: App? = null
    }
}