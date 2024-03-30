package com.woosuk.designsystem

import androidx.compose.runtime.Composable

// 다크모드 판별
@Composable
internal expect fun SystemAppearance(isDark: Boolean)
