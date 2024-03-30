package com.woosuk.domain.model.date

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil
import kotlinx.datetime.toInstant

sealed class DurationFromNow(
    val value: DateTimePeriod,
) {
    data class LessThanOneMinute(val duration: DateTimePeriod) : DurationFromNow(duration)

    data class OneMinuteToOneHour(val duration: DateTimePeriod) : DurationFromNow(duration)

    data class OneHourToOneDay(val duration: DateTimePeriod) : DurationFromNow(duration)

    data class OneDayToSevenDay(val duration: DateTimePeriod) : DurationFromNow(duration)

    data class SevenDayToOneYear(val duration: DateTimePeriod) : DurationFromNow(duration)

    data class OverOneYear(val duration: DateTimePeriod) : DurationFromNow(duration)

    val seconds = value.seconds
    val minutes = value.minutes
    val hours = value.hours
    val days = value.days
    val years = value.days / DAYS_FOR_YEAR

    companion object {
        fun from(date: LocalDateTime): DurationFromNow {
            val dateTimePeriod =
                Clock.System.now().periodUntil(
                    date.toInstant(TimeZone.currentSystemDefault()),
                    TimeZone.currentSystemDefault(),
                )
            return when {
                dateTimePeriod.isLessThanOneMinute() -> LessThanOneMinute(dateTimePeriod)
                dateTimePeriod.isLessThanOneHour() -> OneMinuteToOneHour(dateTimePeriod)
                dateTimePeriod.isLessThanOneDay() -> OneHourToOneDay(dateTimePeriod)
                dateTimePeriod.isLessThanWeekDays() -> OneDayToSevenDay(dateTimePeriod)
                dateTimePeriod.isLessOneYear() -> SevenDayToOneYear(dateTimePeriod)
                else -> OverOneYear(dateTimePeriod)
            }
        }

        private fun DateTimePeriod.isLessThanOneMinute() = minutes < 1

        private fun DateTimePeriod.isLessThanOneHour() = hours < 1

        private fun DateTimePeriod.isLessThanOneDay() = days < 1

        private fun DateTimePeriod.isLessThanWeekDays() = days < DAYS_FOR_WEEK

        private fun DateTimePeriod.isLessOneYear() = days < DAYS_FOR_YEAR

        private const val DAYS_FOR_YEAR = 365
        private const val DAYS_FOR_WEEK = 7
    }
}
