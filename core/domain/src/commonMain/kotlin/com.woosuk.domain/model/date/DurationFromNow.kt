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
    val years = value.years

    companion object {
        fun from(date: LocalDateTime): DurationFromNow {
            val dateTimePeriod =
                date.toInstant(TimeZone.currentSystemDefault()).periodUntil(
                    Clock.System.now(),
                    TimeZone.currentSystemDefault(),
                )
            return when {
                dateTimePeriod.isOverOneYear() -> OverOneYear(dateTimePeriod)
                dateTimePeriod.isOverWeekDays() -> SevenDayToOneYear(dateTimePeriod)
                dateTimePeriod.isOverOneDay() -> OneDayToSevenDay(dateTimePeriod)
                dateTimePeriod.isOverOneHour() -> OneHourToOneDay(dateTimePeriod)
                dateTimePeriod.isOverOneMinute() -> OneMinuteToOneHour(dateTimePeriod)
                else -> LessThanOneMinute(dateTimePeriod)
            }
        }

        private fun DateTimePeriod.isOverOneMinute() = minutes >= 1

        private fun DateTimePeriod.isOverOneHour() = hours >= 1

        private fun DateTimePeriod.isOverOneDay() = days >= 1

        private fun DateTimePeriod.isOverWeekDays() = days >= DAYS_FOR_WEEK

        private fun DateTimePeriod.isOverOneYear() = years >= 1

        private const val DAYS_FOR_WEEK = 7
    }
}
