package com.woosuk.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow

@Composable
fun getRootNavigator(): Navigator {
    var currentNavigator = LocalNavigator.currentOrThrow

    while (currentNavigator.level != 0) {
        currentNavigator = currentNavigator.parent!!
    }
    return currentNavigator
}

@Composable
fun getSharedScreenModel(): SharedScreenModel {
    val rootNavigator = getRootNavigator()
    return rootNavigator.getNavigatorScreenModel<SharedScreenModel>()
}
