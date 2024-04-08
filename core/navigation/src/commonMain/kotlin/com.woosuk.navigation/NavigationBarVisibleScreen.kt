package com.woosuk.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

abstract class NavigationBarVisibleScreen : Screen {
    @Composable
    override fun Content() {
        val sharedScreenModel = getSharedScreenModel()
        sharedScreenModel.setBottomBarVisibility(true)
        ScreenContent()
    }

    @Composable
    abstract fun ScreenContent()
}
