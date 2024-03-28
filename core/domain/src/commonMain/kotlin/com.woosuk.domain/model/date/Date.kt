package com.woosuk.domain.model.date

import kotlinx.datetime.LocalDateTime
import kotlin.jvm.JvmInline

@JvmInline
value class Date(
    val date: LocalDateTime,
) {
    val durationFromNow: DurationFromNow
        get() = DurationFromNow.from(date)
}
