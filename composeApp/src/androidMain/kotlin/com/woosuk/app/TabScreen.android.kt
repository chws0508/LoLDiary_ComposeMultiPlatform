package com.woosuk.app

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.woosuk.calendar.CalendarTab
import com.woosuk.home.HomeTab
import com.woosuk.setting.SettingsTab

@Composable
actual fun BackPressHandler(
    tabNavigator: TabNavigator,
    rootNavigator: Navigator,
) {
    val context = LocalContext.current
    var backPressedState by remember { mutableStateOf(true) }
    var backPressedTime = 0L
    val currentTab = tabNavigator.current
    BackHandler {
        when (currentTab) {
            is HomeTab -> {
                if (System.currentTimeMillis() - backPressedTime <= FINISH_TIME) {
                    (context as Activity).finish()
                } else {
                    backPressedState = true
                    Toast.makeText(context, "한 번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
                }
                backPressedTime = System.currentTimeMillis()
            }

            is CalendarTab, is SettingsTab -> tabNavigator.current = HomeTab()
        }
    }
}

private const val FINISH_TIME = 1500L
