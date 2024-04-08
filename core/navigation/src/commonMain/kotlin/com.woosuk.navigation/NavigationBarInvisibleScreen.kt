package com.woosuk.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

abstract class NavigationBarInvisibleScreen : Screen {
    @Composable
    override fun Content() {
        val sharedScreenModel = getSharedScreenModel()
        sharedScreenModel.setBottomBarVisibility(false)
        ScreenContent()
    }

    @Composable
    abstract fun ScreenContent()
}
