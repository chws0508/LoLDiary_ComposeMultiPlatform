package com.woosuk.domain.util

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

fun LocalDate.atStartOfMonth() = LocalDate(year = year, month = month, dayOfMonth = 1)

fun LocalDate.atEndOfMonth() =
    plus(1, DateTimeUnit.MONTH).atStartOfMonth()
        .minus(1, DateTimeUnit.DAY)

fun getCurrentLocalDate(): LocalDate {
    val currentLocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    return LocalDate(
        currentLocalDateTime.year,
        currentLocalDateTime.month,
        currentLocalDateTime.dayOfMonth,
    )
}
