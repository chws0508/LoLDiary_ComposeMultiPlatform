package com.woosuk.domain.usecase

import com.woosuk.domain.model.ErrorState
import com.woosuk.domain.model.match.QueueType
import com.woosuk.domain.model.match.UserMatchInfoList
import com.woosuk.domain.repository.MatchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

class GetUserMatchInfoListByDateUseCase(
    private val matchRepository: MatchRepository,
) {
    operator fun invoke(
        date: LocalDate,
        puuid: String,
        queueType: QueueType? = null,
        onError: (ErrorState) -> Unit,
    ): Flow<UserMatchInfoList> =
        matchRepository.getUserMatchInfos(
            offset = 0,
            loadSize = MAX_LOAD_SIZE,
            startTime = LocalDateTime(date, LocalTime(0, 0, 0)),
            entTime = LocalDateTime(date, LocalTime(23, 59, 59)),
            puuid = puuid,
            onError = onError,
            queueId = queueType?.id,
        ).map { UserMatchInfoList(it) }

    companion object {
        private const val MAX_LOAD_SIZE = 100
    }
}
