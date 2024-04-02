package com.woosuk.domain.repository

import com.woosuk.domain.model.ErrorState
import com.woosuk.domain.model.match.UserMatchInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

interface MatchRepository {
    fun getUserMatchInfos(
        offset: Int,
        loadSize: Int,
        queueId: Int?,
        startTime: LocalDateTime?, // should convert to timeStamp
        entTime: LocalDateTime?, // should convert to timeStamp
        puuid: String,
        onError: (ErrorState) -> Unit,
    ): Flow<List<UserMatchInfo>>
}
