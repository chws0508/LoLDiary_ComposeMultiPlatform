package com.woosuk.matchdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.woosuk.designsystem.ui.BackButtonTopAppBar
import com.woosuk.navigation.NavigationBarInvisibleScreen

class MatchDetailsScreen(
    private val matchId: String,
) : NavigationBarInvisibleScreen() {
    @Composable
    override fun ScreenContent() {
        val navigator = LocalNavigator.currentOrThrow
        MatchDetailsContent(
            onClickBackButton = { navigator.pop() },
        )
    }
}

@Composable
fun MatchDetailsContent(onClickBackButton: () -> Unit) {
    Column {
        BackButtonTopAppBar(
            onClickBackButton = onClickBackButton,
            title = {},
        )
        Text("디테일 화면입니다.")
    }
}
