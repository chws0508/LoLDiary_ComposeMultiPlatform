package com.woosuk.matchdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.woosuk.designsystem.ui.BackButtonTopAppBar

class MatchDetailsScreen(
    val previousTab: Tab,
    private val matchId: String,
) : Tab {
    override val options: TabOptions
        @Composable
        get() {
            return remember {
                TabOptions(
                    index = 0u,
                    title = "matchDetails",
                )
            }
        }

    @Composable
    override fun Content() {
        val navigator = LocalTabNavigator.current
        MatchDetailsContent(
            onClickBackButton = { navigator.current = previousTab },
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
