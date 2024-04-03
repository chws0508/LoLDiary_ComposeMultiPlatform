package com.woosuk.calendar

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.woosuk.domain.model.match.UserMatchInfoList
import com.woosuk.domain.usecase.GetCurrentAccountUseCase
import com.woosuk.domain.usecase.GetUserMatchInfoListByDateUseCase
import com.woosuk.domain.util.getCurrentLocalDate
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class CalendarScreenModel(
    private val getUserMatchInfoListByDateUseCase: GetUserMatchInfoListByDateUseCase,
    private val getCurrentAccountUseCase: GetCurrentAccountUseCase,
) : ScreenModel {
    val yearRange = IntRange(getCurrentLocalDate().year - 2, getCurrentLocalDate().year + 1)

    private val _selectedDate = MutableStateFlow<LocalDate>(getCurrentLocalDate())
    val selectedDate = _selectedDate.asStateFlow()

    private val _uiEvent = Channel<CalendarScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val userMatchInfoList: StateFlow<UserMatchInfoList> =
        selectedDate.map {
            val puuid = getCurrentAccountUseCase()?.puuid ?: throw IllegalStateException()
            getUserMatchInfoListByDateUseCase(
                date = it,
                puuid = puuid,
                onError = {
                    screenModelScope.launch { _uiEvent.send(CalendarScreenUiEvent.Failure) }
                },
            ).firstOrNull() ?: UserMatchInfoList(emptyList())
        }.stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            UserMatchInfoList(emptyList()),
        )

    fun onSelectDate(date: LocalDate) {
        _selectedDate.update { date }
    }
}
