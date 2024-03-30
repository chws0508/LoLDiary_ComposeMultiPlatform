package com.woosuk.ui

import com.woosuk.domain.model.date.Date
import com.woosuk.domain.model.date.DurationFromNow

fun Date.toRelativeString(): String {
    return when (durationFromNow) {
        is DurationFromNow.LessThanOneMinute -> "${durationFromNow.seconds}초 전"
        is DurationFromNow.OneDayToSevenDay -> "${durationFromNow.days}일 전"
        is DurationFromNow.OneHourToOneDay -> "${durationFromNow.days}시간 전"
        is DurationFromNow.OneMinuteToOneHour -> "${durationFromNow.minutes}분 전"
        is DurationFromNow.OverOneYear -> "${durationFromNow.years}년 전"
        is DurationFromNow.SevenDayToOneYear -> "${date.monthNumber}월 ${date.dayOfMonth}일"
    }
}
