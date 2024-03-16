package com.woosuk.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

class HomeTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "홈"
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon,
                )
            }
        }

    @Composable
    override fun Content() {
        val homeScreenModel = getScreenModel<HomeScreenModel>()
        val userUiState = homeScreenModel.userUiState.collectAsState().value

        when (userUiState) {
            UserUiState.Fail -> Text("불러오는데에 실패하였어요")
            UserUiState.Loading -> CircularProgressIndicator()
            is UserUiState.Success -> Text(userUiState.user.toString())
        }
    }
}
