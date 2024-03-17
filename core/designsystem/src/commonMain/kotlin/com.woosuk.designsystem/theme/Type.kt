package com.woosuk.designsystem.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class WoosukTypography(
    val heading1: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 40.sp,
            lineHeight = 17.6.sp,
            letterSpacing = (-1).sp,
        ),
    val heading2: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 32.sp,
            lineHeight = 16.sp,
            letterSpacing = (-1).sp,
        ),
    val heading3: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 30.sp,
            lineHeight = 16.sp,
            letterSpacing = (-0.5).sp,
        ),
    val heading4: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 28.sp,
            lineHeight = 21.6.sp,
            letterSpacing = (-1).sp,
        ),
    val heading5: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
            lineHeight = 23.2.sp,
            letterSpacing = (-1).sp,
        ),
    val bodyDoubleExtraLageMedium: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            lineHeight = 20.8.sp,
            letterSpacing = 0.sp,
        ),
    val bodyDoubleExtraLargeRegular: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            lineHeight = 20.8.sp,
            letterSpacing = 0.sp,
        ),
    val bodyExtraLargeMedium: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            lineHeight = 23.2.sp,
            letterSpacing = (-1).sp,
        ),
    val bodyExtraLargeRegular: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 23.2.sp,
            letterSpacing = (-1).sp,
        ),
    val bodyLargeMedium: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 22.4.sp,
            letterSpacing = (-1).sp,
        ),
    val bodyLargeRegular: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 22.4.sp,
            letterSpacing = (-1).sp,
        ),
    val bodyMediumMedium: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 23.2.sp,
            letterSpacing = 0.sp,
        ),
    val bodyMediumRegular: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 22.4.sp,
            letterSpacing = 0.sp,
        ),
    val bodySmallMedium: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 24.0.sp,
            letterSpacing = 0.sp,
        ),
    val bodySmallRegular: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.sp,
        ),
    val bodyExtraSmallMedium: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            lineHeight = 25.2.sp,
            letterSpacing = 0.sp,
        ),
    val bodyExtraSmallRegular: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
            lineHeight = 25.2.sp,
            letterSpacing = 0.sp,
        ),
    val display1: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            fontSize = 56.sp,
            lineHeight = 17.6.sp,
            letterSpacing = 0.sp,
        ),
    val display2: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 28.sp,
            lineHeight = 21.6.sp,
            letterSpacing = 0.sp,
        ),
    val display3: TextStyle =
        TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            fontSize = 28.sp,
            lineHeight = 19.2.sp,
            letterSpacing = 0.sp,
        ),
)
