package com.woosuk.app

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.woosuk.onboarding.OnboardingRoute

@Composable
internal fun App() {
    Surface {
        OnboardingRoute()
    }
}
