package com.woosuk.app

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.woosuk.onboarding.OnboardingScreen

class MainScreen(
    private val showSnackBar: (String) -> Unit,
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        navigator.push(OnboardingScreen(onShowSnackBar = showSnackBar))
    }
}
