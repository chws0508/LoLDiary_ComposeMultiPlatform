package com.woosuk.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.CoroutineScope

val LocalCustomColors =
    staticCompositionLocalOf {
        WoosukColor()
    }

val LocalCustomTypography =
    staticCompositionLocalOf {
        WoosukTypography()
    }

val LocalCustomPadding =
    staticCompositionLocalOf {
        WoosukPadding()
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
        LocalCustomPadding provides WoosukPadding(),
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
    val padding: WoosukPadding
        @Composable
        get() = LocalCustomPadding.current
}

@Composable
internal expect fun SystemAppearance(isDark: Boolean)
