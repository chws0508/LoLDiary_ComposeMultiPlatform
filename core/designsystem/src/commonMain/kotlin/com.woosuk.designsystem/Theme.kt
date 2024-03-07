package com.woosuk.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalCustomColors =
    staticCompositionLocalOf {
        WoosukColor()
    }

val LocalCustomTypography =
    staticCompositionLocalOf {
        WoosukTypography()
    }

@Composable
fun WoosukTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            darkTheme -> darkColor
            else -> lightColor
        }
    SystemAppearance(!darkTheme)
    CompositionLocalProvider(
        LocalCustomColors provides colorScheme,
        LocalCustomTypography provides WoosukTypography(),
        content = content,
    )
}

object WoosukTheme {
    val colors: WoosukColor
        @Composable
        get() = LocalCustomColors.current
    val typography: WoosukTypography
        @Composable
        get() = LocalCustomTypography.current
}

@Composable
internal expect fun SystemAppearance(isDark: Boolean)
