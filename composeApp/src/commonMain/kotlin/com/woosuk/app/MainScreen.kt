package com.woosuk.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.woosuk.onboarding.OnboardingScreen

class MainScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val mainScreenModel = getScreenModel<MainScreenModel>()
        LaunchedEffect(null) {
            mainScreenModel.isLogin.collect { puuid ->
                if (puuid != null) {
                    navigator.replace(TabScreen(puuid))
                } else {
                    navigator.replace(OnboardingScreen())
                }
            }
        }
    }
}
