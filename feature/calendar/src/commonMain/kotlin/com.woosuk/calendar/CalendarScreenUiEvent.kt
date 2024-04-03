package com.woosuk.calendar

sealed interface CalendarScreenUiEvent {
    data object Failure : CalendarScreenUiEvent
}
