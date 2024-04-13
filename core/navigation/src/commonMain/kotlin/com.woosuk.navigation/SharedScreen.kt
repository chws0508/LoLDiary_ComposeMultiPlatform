package com.woosuk.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class SharedScreen : ScreenProvider {
    data class TabScreen(val initKey: String) : SharedScreen()

    data object SettingTab : SharedScreen()

    data object CalendarTab : SharedScreen()

    data object HomeTab : SharedScreen()

    data object OnboardingScreen : SharedScreen()

    data class MatchDetailsScreen(val matchId: String) : SharedScreen()
}
