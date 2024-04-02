package com.woosuk.ui

fun Float.roundToDecimals(decimals: Int): Float {
    var flag = 1
    repeat(decimals) {
        flag *= 10
    }
    return (this * flag).toInt().toFloat() / flag
}
