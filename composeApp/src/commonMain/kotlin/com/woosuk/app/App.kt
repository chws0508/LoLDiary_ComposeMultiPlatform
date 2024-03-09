package com.woosuk.app

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.woosuk.data.di.repositoryModule
import com.woosuk.database.dataSourceModule
import com.woosuk.database.databaseModule
import com.woosuk.domain.di.useCaseModule
import com.woosuk.network.di.networkModule
import com.woosuk.network.di.serviceModule
import com.woosuk.onboarding.OnboardingScreenModel
import kotlinx.coroutines.launch
import org.koin.dsl.module

@OptIn(InternalVoyagerApi::class)
@Composable
internal fun App() {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutine = rememberCoroutineScope()
    fun showSnackBar(message: String) {
        coroutine.launch { snackbarHostState.showSnackbar(message) }
    }
    Navigator(MainScreen(::showSnackBar)) {
        Scaffold(
            content = { CurrentScreen() },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        )
    }
}
val screenModelModule = module {
    factory { OnboardingScreenModel(get(), get()) }
}

val sharedModules = listOf(
    useCaseModule, serviceModule, networkModule, repositoryModule, screenModelModule,
    dataSourceModule, databaseModule,
)
