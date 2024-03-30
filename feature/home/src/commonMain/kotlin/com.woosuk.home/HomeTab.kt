package com.woosuk.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.woosuk.designsystem.LocalSnackbarController
import com.woosuk.designsystem.theme.WoosukTheme
import com.woosuk.domain.model.UserMatchInfo

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
        val snackBarController = LocalSnackbarController.current
        val homeScreenModel = getScreenModel<HomeScreenModel>()
        val userUiState = homeScreenModel.userUiState.collectAsState().value
        val matchInfoList = homeScreenModel.matchInfoList.collectAsLazyPagingItems()

        LaunchedEffect(null) {
            homeScreenModel.sideEffect.collect {
                when (it) {
                    HomeSideEffect.MatchInfoLoadFail -> snackBarController.showMessage("경기 정보를 불러올 수 없어요")
                    HomeSideEffect.UserInfoLoadFail -> snackBarController.showMessage("유저 정보를 불러올 수 없어요")
                }
            }
        }
        HomeScreenContent(userUiState, matchInfoList)
    }
}

@Composable
fun HomeScreenContent(
    userUiState: UserUiState,
    matchInfoList: LazyPagingItems<UserMatchInfo>,
) {
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
        when (matchInfoList.loadState.refresh) {
            is LoadState.Error -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text("매치 데이터를 불러올 수 없어요", modifier = Modifier.align(Alignment.Center))
                }
            }

            LoadState.Loading ->
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

            is LoadState.NotLoading -> {
                LazyColumn {
                    items(
                        matchInfoList.itemCount,
                        key = { matchInfoList[it]!!.gameInfo.matchId },
                    ) {
                        val matchInfo = matchInfoList[it] ?: throw IllegalStateException()
                        Column {
                            Text("${matchInfo.account.nickName}")
                            Text("${matchInfo.gameInfo.queueType}")
                            Text("${matchInfo.champion.name}")
                            Text("${matchInfo.gameInfo.endAt.date}")
                            Text("${matchInfo.gameInfo.isWin}")
                        }
                    }
                }
            }
        }
    }
}
