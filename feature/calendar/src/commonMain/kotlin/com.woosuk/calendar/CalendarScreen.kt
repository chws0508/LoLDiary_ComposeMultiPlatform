package com.woosuk.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.woosuk.designsystem.LocalSnackbarController
import com.woosuk.designsystem.theme.WoosukTheme
import com.woosuk.domain.model.match.UserMatchInfoList
import com.woosuk.navigation.NavigationBarVisibleScreen
import com.woosuk.navigation.SharedScreen
import com.woosuk.ui.MatchInfoItem
import com.woosuk.ui.roundToDecimals
import kotlinx.datetime.LocalDate

class CalendarScreen : NavigationBarVisibleScreen() {
    @Composable
    override fun ScreenContent() {
        val screenModel = getScreenModel<CalendarScreenModel>()
        val snackBarController = LocalSnackbarController.current
        val selectedDate = screenModel.selectedDate.collectAsState().value
        val userMatchInfoList = screenModel.userMatchInfoList.collectAsState().value
        val currentNavigator = LocalNavigator.currentOrThrow

        LaunchedEffect(null) {
            screenModel.uiEvent.collect {
                when (it) {
                    CalendarScreenUiEvent.Failure -> snackBarController.showMessage("더이상 데이터를 불러올 수 없어요! 재정비후 다시 시도해주세요!")
                }
            }
        }

        CalendarScreen(
            selectedDate = selectedDate,
            yearRange = screenModel.yearRange,
            onSelectDate = screenModel::onSelectDate,
            userMatchInfoList = userMatchInfoList,
            onNavigateToMatchDetails = {
                currentNavigator.push(it)
            },
        )
    }
}

@Composable
fun CalendarScreen(
    selectedDate: LocalDate,
    yearRange: IntRange,
    onSelectDate: (LocalDate) -> Unit,
    userMatchInfoList: UserMatchInfoList,
    onNavigateToMatchDetails: (Screen) -> Unit,
) {
    LazyColumn {
        item {
            HorizontalCalendar(
                yearRange = yearRange,
                selectedDate = selectedDate,
                onChangeDate = onSelectDate,
            )
        }

        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "${selectedDate.year}년 ${selectedDate.monthNumber}월 ${selectedDate.dayOfMonth}일",
                    style = WoosukTheme.typography.heading5,
                    textAlign = TextAlign.Center,
                    modifier =
                        Modifier.padding(
                            vertical = WoosukTheme.padding.BasicContentPadding,
                        ).align(Alignment.Center),
                )
            }
        }
        MatchInfoSection(userMatchInfoList, onNavigateToMatchDetails)
    }
}

@Suppress("ktlint:standard:function-naming")
fun LazyListScope.MatchInfoSection(
    userMatchInfoList: UserMatchInfoList,
    onClickMatch: (Screen) -> Unit,
) {
    if (userMatchInfoList.list.isEmpty()) {
        item {
            Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
                Text(
                    "플레이한 기록이 없어요",
                    style = WoosukTheme.typography.heading5,
                    modifier =
                        Modifier.align(
                            Alignment.Center,
                        ),
                )
            }
        }
    } else {
        item {
            Row(
                modifier = Modifier.padding(horizontal = WoosukTheme.padding.BasicHorizontalPadding),
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    "${userMatchInfoList.totalWins}승 ${userMatchInfoList.totalLosses}패",
                    style = WoosukTheme.typography.bodyExtraLargeMedium,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    "(${userMatchInfoList.winRate.roundToDecimals(2)})%",
                    style = WoosukTheme.typography.bodySmallRegular,
                    color = WoosukTheme.colors.Black60,
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        items(
            items = userMatchInfoList.list,
            key = { it.gameInfo.matchId },
        ) { userMatchInfo ->
            val matchDetailsScreen =
                rememberScreen(
                    SharedScreen.MatchDetailsScreen(
                        userMatchInfo.gameInfo.matchId,
                    ),
                )
            MatchInfoItem(
                userMatchInfo = userMatchInfo,
                onClickView = {
                    onClickMatch(matchDetailsScreen)
                },
            )
            Spacer(modifier = Modifier.height(WoosukTheme.padding.BasicContentPadding))
        }
    }
}
