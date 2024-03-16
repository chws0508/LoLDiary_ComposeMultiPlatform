package com.woosuk.app

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.registry.screenModule
import com.woosuk.home.HomeTab
import com.woosuk.home.SettingsTab
import com.woosuk.navigation.SharedScreen
import com.woosuk.onboarding.OnboardingScreen
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
