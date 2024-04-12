package com.woosuk.app

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.woosuk.calendar.CalendarScreenModel
import com.woosuk.calendar.CalendarTab
import com.woosuk.data.di.repositoryModule
import com.woosuk.database.dataSourceModule
import com.woosuk.database.databaseModule
import com.woosuk.designsystem.LocalSnackbarController
import com.woosuk.designsystem.SnackBarController
import com.woosuk.designsystem.theme.WoosukTheme
import com.woosuk.domain.di.useCaseModule
import com.woosuk.home.HomeScreenModel
import com.woosuk.home.HomeTab
import com.woosuk.matchdetails.MatchDetailsScreen
import com.woosuk.matchdetails.MatchDetailsScreenModel
import com.woosuk.navigation.SharedScreen
import com.woosuk.navigation.SharedScreenModel
import com.woosuk.network.di.networkModule
import com.woosuk.network.di.serviceModule
import com.woosuk.onboarding.OnboardingScreen
import com.woosuk.onboarding.OnboardingScreenModel
import com.woosuk.setting.SettingScreenModel
import com.woosuk.setting.SettingsTab
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.dsl.module

@Composable
internal fun App() {
    Napier.base(DebugAntilog())
    WoosukTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        val coroutine = rememberCoroutineScope()
        CompositionLocalProvider(
            LocalSnackbarController provides SnackBarController(coroutine, snackbarHostState),
        ) {
            Navigator(
                MainScreen(),
                onBackPressed = { true },
            ) {
                CurrentScreen()
            }
        }
    }
}

fun registerScreen() {
    ScreenRegistry {
        register<SharedScreen.OnboardingScreen> {
            OnboardingScreen()
        }
        register<SharedScreen.HomeTab> {
            HomeTab()
        }
        register<SharedScreen.SettingTab> {
            SettingsTab()
        }
        register<SharedScreen.TabScreen> {
            TabScreen()
        }
        register<SharedScreen.CalendarTab> {
            CalendarTab()
        }
        register<SharedScreen.MatchDetailsScreen> {
            MatchDetailsScreen(matchId = it.matchId)
        }
    }
}

val screenModelModule =
    module {
        factory { OnboardingScreenModel(get(), get()) }
        factory { MainScreenModel(get(), get()) }
        factory { HomeScreenModel(get(), get()) }
        factory { CalendarScreenModel(get(), get()) }
        factory { MatchDetailsScreenModel(get()) }
        factory { SettingScreenModel(get(), get()) }
        single { SharedScreenModel() }
    }

val sharedModules =
    listOf(
        useCaseModule,
        serviceModule,
        networkModule,
        repositoryModule,
        screenModelModule,
        dataSourceModule,
        databaseModule,
    )
