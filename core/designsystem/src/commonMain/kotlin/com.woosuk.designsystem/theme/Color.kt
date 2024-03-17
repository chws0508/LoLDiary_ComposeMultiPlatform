package com.woosuk.designsystem.theme

import androidx.compose.ui.graphics.Color

val primary100: Color = Color(0xFF2752E7)
val primary80: Color = Color(0xFF5275EC)
val primary60: Color = Color(0xFF476AE5)
val primary40: Color = Color(0xFFD0DBFF)
val primary20: Color = Color(0xFFF5F8FE)

val secondary100: Color = Color(0xFFFFBE55)
val secondary80: Color = Color(0xFFFFD899)
val secondary60: Color = Color(0xFFFFE5BB)
val secondary40: Color = Color(0xFFFFF2DD)
val secondary20: Color = Color(0xFFFFF9EF)

val black100: Color = Color(0xFF111111)
val black80: Color = Color(0xFF707070)
val black60: Color = Color(0xFFA0A0A0)
val black40: Color = Color(0xFFCFCFCF)
val black20: Color = Color(0xFFF3F3F3)
val black0: Color = Color(0xFFFFFFFF)

val success: Color = Color(0xFF3F845F)
val error: Color = Color(0xFFE25C5C)
val warning: Color = Color(0xFFE4C65B)
val info: Color = Color(0xFF2685CA)

data class WoosukColor(
    val Primary100: Color = primary100,
    val Primary80: Color = primary80,
    val Primary60: Color = primary60,
    val Primary40: Color = primary40,
    val Primary20: Color = primary20,
    val Secondary100: Color = secondary100,
    val Secondary80: Color = secondary80,
    val Secondary60: Color = secondary60,
    val Secondary40: Color = secondary40,
    val Secondary20: Color = secondary20,
    val Black100: Color = black100,
    val Black80: Color = black80,
    val Black60: Color = black60,
    val Black40: Color = black40,
    val Black20: Color = black20,
    val Black0: Color = black0,
    val Success: Color = success,
    val Error: Color = error,
    val Warning: Color = warning,
    val Info: Color = info,
)

val lightColor = WoosukColor()
val darkColor = WoosukColor()
