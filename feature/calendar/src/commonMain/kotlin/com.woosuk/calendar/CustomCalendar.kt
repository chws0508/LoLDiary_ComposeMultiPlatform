package com.woosuk.calendar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.woosuk.designsystem.theme.WoosukTheme.colors
import com.woosuk.designsystem.theme.WoosukTheme.typography
import com.woosuk.designsystem.ui.conditional
import com.woosuk.designsystem.ui.noRippleClickable
import com.woosuk.domain.util.atEndOfMonth
import com.woosuk.domain.util.getCurrentLocalDate
import com.woosuk.ui.getKoreanString
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalCalendar(
    modifier: Modifier = Modifier,
    yearRange: IntRange,
    selectedDate: LocalDate,
    onChangeDate: (LocalDate) -> Unit,
) {
    // page는 0부터 시작하기 때문에 getMonthValue - 1을 해줘야 함
    val initialPage =
        (selectedDate.year - yearRange.first) * 12 + selectedDate.monthNumber - 1
    var currentMonth by remember { mutableStateOf(selectedDate) }
    var currentPage by remember { mutableStateOf(initialPage) }
    val pagerState =
        rememberPagerState(
            initialPage = initialPage,
            pageCount = {
                initialPage + 1
            },
        )

    LaunchedEffect(pagerState.currentPage) {
        val addMonth = (pagerState.currentPage - currentPage)
        currentMonth = currentMonth.plus(addMonth, DateTimeUnit.MONTH)
        currentPage = pagerState.currentPage
    }

    Column(modifier = modifier) {
        val headerText = "${currentMonth.year}년 ${currentMonth.monthNumber}월"
        CalendarHeader(
            modifier = Modifier.padding(20.dp),
            text = headerText,
        )
        HorizontalPager(
            state = pagerState,
        ) { page ->
            val date =
                LocalDate(
                    yearRange.first + page / 12,
                    page % 12 + 1,
                    1,
                )
            if (page in pagerState.currentPage - 1..pagerState.currentPage + 1) { // 페이징 성능 개선을 위한 조건문
                CalendarMonthItem(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                    firstDate = date,
                    selectedDate = selectedDate,
                    onSelectedDate = onChangeDate,
                )
            }
        }
        HorizontalDivider()
    }
}

@Composable
fun CalendarHeader(
    modifier: Modifier = Modifier,
    text: String,
) {
    Box(modifier = modifier) {
        Text(
            text = text,
            style = typography.heading5,
        )
    }
}

@Composable
fun CalendarMonthItem(
    modifier: Modifier = Modifier,
    firstDate: LocalDate,
    selectedDate: LocalDate,
    onSelectedDate: (LocalDate) -> Unit,
) {
    val lastDate by remember { mutableStateOf(firstDate.atEndOfMonth()) }
    val firstDayOfWeek by remember { mutableStateOf(firstDate.dayOfWeek.ordinal) }
    val days by remember { mutableStateOf(IntRange(1, lastDate.dayOfMonth).toList()) }

    Column(modifier = modifier) {
        DayOfWeekView()
        LazyVerticalGrid(
            modifier = Modifier.wrapContentSize().heightIn(min = 220.dp, max = 260.dp),
            columns = GridCells.Fixed(7),
        ) {
            for (i in 1 until firstDayOfWeek) { // 처음 날짜가 시작하는 요일 전까지 빈 박스 생성
                item {
                    Box(
                        modifier =
                            Modifier
                                .size(30.dp)
                                .padding(top = 10.dp),
                    )
                }
            }
            items(days) { day ->
                val date = LocalDate(firstDate.year, firstDate.month, day)
                CalendarDay(
                    modifier = Modifier.padding(top = 10.dp),
                    date = date,
                    isToday = date == getCurrentLocalDate(),
                    isBeforeDate = date < getCurrentLocalDate(),
                    isLaterDate = date > getCurrentLocalDate(),
                    onSelectedDate = onSelectedDate,
                    selectedDate = selectedDate,
                )
            }
        }
    }
}

@Composable
fun CalendarDay(
    modifier: Modifier = Modifier,
    date: LocalDate,
    isToday: Boolean,
    isBeforeDate: Boolean,
    isLaterDate: Boolean,
    onSelectedDate: (LocalDate) -> Unit,
    selectedDate: LocalDate,
) {
    Column(
        modifier =
            modifier
                .wrapContentSize()
                .size(30.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .conditional(isToday) {
                    background(colors.Secondary40)
                }.conditional(isBeforeDate) {
                    border(
                        border =
                            BorderStroke(
                                width = 1.dp,
                                color = colors.Black20,
                            ),
                    )
                }.conditional(isLaterDate) {
                    this
                }.conditional(selectedDate == date) {
                    background(colors.Primary40)
                }.noRippleClickable {
                    if (!isLaterDate) onSelectedDate(date)
                },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier,
            textAlign = TextAlign.Center,
            text = date.dayOfMonth.toString(),
            style = typography.bodyMediumMedium,
            color = if (isLaterDate) colors.Black40 else colors.Black100,
        )
    }
}

@Composable
fun DayOfWeekView(modifier: Modifier = Modifier) {
    Row(modifier) {
        DayOfWeek.entries.forEach { dayOfweek ->
            Text(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
                text = dayOfweek.getKoreanString(),
                style = typography.bodyMediumMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}
