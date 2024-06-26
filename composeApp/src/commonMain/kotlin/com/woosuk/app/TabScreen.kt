package com.woosuk.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.woosuk.calendar.CalendarTab
import com.woosuk.designsystem.LocalSnackbarController
import com.woosuk.designsystem.theme.WoosukTheme
import com.woosuk.home.HomeTab
import com.woosuk.navigation.getRootNavigator
import com.woosuk.navigation.getSharedScreenModel
import com.woosuk.setting.SettingsTab

class TabScreen(
    private val initKey: String,
) : Screen {
    override val key: ScreenKey
        get() = initKey

    @Composable
    override fun Content() {
        val rootNavigator = getRootNavigator()
        val sharedScreenModel = getSharedScreenModel()
        val showBottomBar by sharedScreenModel.showBottomBar.collectAsState()

        TabNavigator(HomeTab()) {
            BackPressHandler(it, rootNavigator)
            Scaffold(
                snackbarHost = { SnackbarHost(LocalSnackbarController.current.snackBarHostState) },
                content = {
                    Box(modifier = Modifier.fillMaxSize().padding(it)) {
                        HorizontalDivider(modifier = Modifier.align(Alignment.BottomCenter))
                        CurrentTab()
                    }
                },
                bottomBar = {
                    if (showBottomBar) {
                        NavigationBar(
                            containerColor = WoosukTheme.colors.Black0,
                            tonalElevation = 5.dp,
                        ) {
                            TabNavigationItem(HomeTab())
                            TabNavigationItem(CalendarTab())
                            TabNavigationItem(SettingsTab())
                        }
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
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    NavigationBarItem(
        selected = tabNavigator.current.key == tab.key,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) },
        colors =
            NavigationBarItemDefaults.colors(
                indicatorColor = WoosukTheme.colors.Black0,
                selectedIconColor = WoosukTheme.colors.Primary100,
                unselectedIconColor = WoosukTheme.colors.Black80,
            ),
    )
}
