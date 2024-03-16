package com.woosuk.app

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.woosuk.designsystem.LocalSnackbarController
import com.woosuk.home.HomeTab
import com.woosuk.home.SettingsTab
import com.woosuk.navigation.SharedScreen

class TabScreen : Screen {
    @Composable
    override fun Content() {
        val onboardingScreen = rememberScreen(SharedScreen.Onboarding)
        val rootNavigator = LocalNavigator.currentOrThrow
        TabNavigator(HomeTab()) {
            Scaffold(
                modifier = Modifier.safeDrawingPadding(),
                snackbarHost = { SnackbarHost(LocalSnackbarController.current.snackBarHostState) },
                bottomBar = {
                    BottomAppBar {
                        TabNavigationItem(HomeTab(), rootNavigator)
                        TabNavigationItem(
                            SettingsTab { rootNavigator.replace(onboardingScreen) },
                            rootNavigator
                        )
                    }
                },
                content = { CurrentTab() }
            )
        }
    }
}

@Composable
expect fun BackPressHandler(tabNavigator: TabNavigator, rootNavigator: Navigator)

@Composable
private fun RowScope.TabNavigationItem(tab: Tab, rootNavigator: Navigator) {
    val tabNavigator = LocalTabNavigator.current
    BackPressHandler(tabNavigator, rootNavigator)
    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) }
    )
}
