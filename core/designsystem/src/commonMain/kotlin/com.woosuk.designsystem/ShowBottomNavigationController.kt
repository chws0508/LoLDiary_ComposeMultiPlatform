package com.woosuk.designsystem

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf

class ShowBottomNavigationController(
    private val showBottomNavigation: MutableState<Boolean>
) {
    fun showBottomNavigation() {
        showBottomNavigation.value = true
    }

    fun hideBottomNavigation() {
        showBottomNavigation.value=false
    }
}

val LocalShowBottomNaviController = compositionLocalOf<ShowBottomNavigationController> {
    error("No BottomNavi Controller")
}
