package com.woosuk.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab

sealed class SharedScreen : ScreenProvider {
    data class TabScreen(val navigator: Navigator) : SharedScreen()

    data class SettingTab(val navigator: Navigator) : SharedScreen()

    data object CalendarTab : SharedScreen()

    data object HomeTab : SharedScreen()

    data object OnboardingScreen : SharedScreen()

    data class MatchDetailsScreen(val matchId: String, val previousTab: Tab) : SharedScreen()
}
