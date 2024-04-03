package com.woosuk.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.woosuk.navigation.SharedScreen
import io.github.aakira.napier.Napier

class SettingsTab(
    val rootNavigator: Navigator,
) : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "세팅"
            val icon = rememberVectorPainter(Icons.Default.Settings)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon,
                )
            }
        }

    @Composable
    override fun Content() {
        Napier.v(rootNavigator.items.toString(), tag = "wooseok")
        val onboardingScreen = rememberScreen(SharedScreen.OnboardingScreen)
        Button(onClick = {
            rootNavigator.replace(onboardingScreen)
        }) {
            Text("세팅입니다~ 변경하세요~")
        }
    }
}
