package com.woosuk.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class LoLDiaryApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LoLDiaryApp)
            androidLogger()

            modules(sharedModules)
            registerScreen()
        }
    }
}
