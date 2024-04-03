package com.woosuk.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.woosuk.navigation.SharedScreen

class SettingsTab(
    val navigateToOnBoarding: () -> Unit,
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
        val onboardingScreen = rememberScreen(SharedScreen.OnboardingScreen)
        Button(onClick = {
            navigateToOnBoarding()
        }) {
            Text("세팅입니다~ 변경하세요~")
        }
    }
}
