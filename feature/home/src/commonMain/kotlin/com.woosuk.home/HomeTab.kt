package com.woosuk.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.woosuk.designsystem.theme.WoosukTheme

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

        HomeScreenContent(userUiState)
    }
}

@Composable
fun HomeScreenContent(userUiState: UserUiState) {
    Column(modifier = Modifier.fillMaxWidth()) {
        when (userUiState) {
            UserUiState.Fail -> Text("불러오는데에 실패하였어요")
            UserUiState.Loading -> CircularProgressIndicator()
            is UserUiState.Success -> {
                Column(modifier = Modifier.padding(horizontal = WoosukTheme.padding.BasicHorizontalPadding)) {
                    Spacer(modifier = Modifier.height(16.dp))
                    UserProfile(
                        account = userUiState.user.account,
                        profileImageUrl = userUiState.user.profileImageUrl,
                        level = userUiState.user.level,
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    RankInfo(rankInfo = userUiState.user.rankInfo)
                }
            }
        }
    }
}
