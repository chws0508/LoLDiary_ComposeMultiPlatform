package com.woosuk.app

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import com.woosuk.data.di.repositoryModule
import com.woosuk.domain.di.useCaseModule
import com.woosuk.network.di.networkModule
import com.woosuk.network.di.serviceModule
import com.woosuk.onboarding.OnboardingScreenModel
import org.koin.compose.KoinApplication
import org.koin.dsl.module

@Composable
internal fun App() {
    KoinApplication(application = {
        modules(useCaseModule, serviceModule, networkModule, repositoryModule, screenModelModule)
    }) {
        Surface(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.safeDrawing)
        ) {
            Navigator(MainRoute())
        }
    }
}

val screenModelModule = module {
    factory { OnboardingScreenModel(get()) }
}
