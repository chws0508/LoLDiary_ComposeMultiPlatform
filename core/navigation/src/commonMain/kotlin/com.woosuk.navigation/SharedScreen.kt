package com.woosuk.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.navigator.Navigator

sealed class SharedScreen : ScreenProvider {
    data object HomeTab : SharedScreen()

    data class SettingTab(val navigateToOnboarding: () -> Unit) : SharedScreen()

    data object CalendarTab : SharedScreen()

    data object OnboardingScreen : SharedScreen()

    data class TabScreen(val navigator: Navigator) : SharedScreen()
}
