package com.woosuk.app

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import com.woosuk.designsystem.theme.WoosukTheme
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
                snackbarHost = { SnackbarHost(LocalSnackbarController.current.snackBarHostState) },
                content = {
                    Box(modifier = Modifier.padding(it)) {
                        CurrentTab()
                    }
                },
                bottomBar = {
                    NavigationBar(
                        containerColor = WoosukTheme.colors.Black0,
                        modifier =
                            Modifier.border(
                                border =
                                    BorderStroke(
                                        width = 1.dp,
                                        color = WoosukTheme.colors.Black40,
                                    ),
                            ),
                        tonalElevation = 5.dp,
                    ) {
                        TabNavigationItem(HomeTab(), rootNavigator)
                        TabNavigationItem(
                            SettingsTab { rootNavigator.replace(onboardingScreen) },
                            rootNavigator,
                        )
                    }
                },
                containerColor = WoosukTheme.colors.Black0,
            )
        }
    }
}

@Composable
expect fun BackPressHandler(
    tabNavigator: TabNavigator,
    rootNavigator: Navigator,
)

@Composable
private fun RowScope.TabNavigationItem(
    tab: Tab,
    rootNavigator: Navigator,
) {
    val tabNavigator = LocalTabNavigator.current
    BackPressHandler(tabNavigator, rootNavigator)
    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) },
    )
}
