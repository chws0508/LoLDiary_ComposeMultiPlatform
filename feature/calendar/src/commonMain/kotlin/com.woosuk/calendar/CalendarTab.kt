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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EditCalendar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.woosuk.designsystem.LocalSnackbarController
import com.woosuk.designsystem.theme.WoosukTheme
import com.woosuk.domain.model.match.UserMatchInfoList
import com.woosuk.ui.MatchInfoItem
import com.woosuk.ui.roundToDecimals

class CalendarTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "캘린더"
            val icon = rememberVectorPainter(Icons.Rounded.EditCalendar)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon,
                )
            }
        }

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<CalendarScreenModel>()
        val snackBarController = LocalSnackbarController.current
        val selectedDate = screenModel.selectedDate.collectAsState().value
        val userMatchInfoList = screenModel.userMatchInfoList.collectAsState().value

        LaunchedEffect(null) {
            screenModel.uiEvent.collect {
                when (it) {
                    CalendarScreenUiEvent.Failure -> snackBarController.showMessage("더이상 데이터를 불러올 수 없어요! 재정비후 다시 시도해주세요!")
                }
            }
        }

        LazyColumn {
            item {
                HorizontalCalendar(
                    yearRange = screenModel.yearRange,
                    selectedDate = selectedDate,
                    onChangeDate = screenModel::onSelectDate,
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
            MatchInfoSection(userMatchInfoList)
        }
    }
}

@Suppress("ktlint:standard:function-naming")
fun LazyListScope.MatchInfoSection(userMatchInfoList: UserMatchInfoList) {
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
            MatchInfoItem(userMatchInfo = userMatchInfo)
            Spacer(modifier = Modifier.height(WoosukTheme.padding.BasicContentPadding))
        }
    }
}
