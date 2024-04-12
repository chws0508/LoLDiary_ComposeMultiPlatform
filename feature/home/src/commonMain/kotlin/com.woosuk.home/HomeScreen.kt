package com.woosuk.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.woosuk.designsystem.LocalSnackbarController
import com.woosuk.designsystem.theme.WoosukTheme
import com.woosuk.domain.model.match.UserMatchInfo
import com.woosuk.navigation.NavigationBarVisibleScreen
import com.woosuk.navigation.SharedScreen
import com.woosuk.navigation.getSharedScreenModel
import com.woosuk.ui.MatchInfoItem

class HomeScreen : NavigationBarVisibleScreen() {
    @Composable
    override fun ScreenContent() {
        val navigator = LocalNavigator.currentOrThrow
        val snackBarController = LocalSnackbarController.current
        val homeScreenModel = navigator.getNavigatorScreenModel<HomeScreenModel>()
        val userUiState = homeScreenModel.userUiState.collectAsState().value
        val matchInfoList = homeScreenModel.matchInfoList.collectAsLazyPagingItems()
        val sharedScreenModel = getSharedScreenModel()
        sharedScreenModel.setBottomBarVisibility(true)

        LaunchedEffect(null) {
            homeScreenModel.sideEffect.collect {
                when (it) {
                    HomeSideEffect.MatchInfoLoadFail -> snackBarController.showMessage("경기 정보를 불러올 수 없어요")
                    HomeSideEffect.UserInfoLoadFail -> snackBarController.showMessage("유저 정보를 불러올 수 없어요")
                }
            }
        }

        HomeScreenContent(
            userUiState = userUiState,
            matchInfoList = matchInfoList,
            onRefreshClick = homeScreenModel::refresh,
            navigateToMatchDetails = {
                navigator.push(it)
            },
        )
    }
}

@Composable
fun HomeScreenContent(
    userUiState: UserUiState,
    matchInfoList: LazyPagingItems<UserMatchInfo>,
    onRefreshClick: () -> Unit,
    navigateToMatchDetails: (Screen) -> Unit,
) {
    when (userUiState) {
        UserUiState.Fail ->
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    "불러오는데에 실패하였어요",
                    modifier =
                        Modifier.align(
                            Alignment.Center,
                        ),
                )
            }

        UserUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is UserUiState.Success -> {
            Column {
                UserProfileTopBar(
                    account = userUiState.user.account,
                    profileImageUrl = userUiState.user.profileImageUrl,
                    level = userUiState.user.level,
                    onRefreshClick = onRefreshClick,
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        RankInfoList(
                            modifier = Modifier.padding(horizontal = WoosukTheme.padding.BasicHorizontalPadding),
                            rankInfoList =
                                listOf(
                                    userUiState.user.soloRankInfo,
                                    userUiState.user.freeRankInfo,
                                ),
                        )
                        Spacer(Modifier.height(WoosukTheme.padding.LargeVerticalPadding))
                    }

                    when (matchInfoList.loadState.refresh) {
                        is LoadState.Error -> {
                            item {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    Text(
                                        "매치 데이터를 불러올 수 없어요",
                                        modifier = Modifier.align(Alignment.Center),
                                    )
                                }
                            }
                        }

                        LoadState.Loading ->
                            item {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                                }
                            }

                        is LoadState.NotLoading -> {
                            items(
                                matchInfoList.itemCount,
                            ) {
                                val matchInfo = matchInfoList[it]!!
                                val matchDetailsScreen =
                                    rememberScreen(
                                        SharedScreen.MatchDetailsScreen(matchInfo.gameInfo.matchId),
                                    )
                                MatchInfoItem(
                                    userMatchInfo = matchInfo,
                                    onClickView = {
                                        navigateToMatchDetails(matchDetailsScreen)
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
