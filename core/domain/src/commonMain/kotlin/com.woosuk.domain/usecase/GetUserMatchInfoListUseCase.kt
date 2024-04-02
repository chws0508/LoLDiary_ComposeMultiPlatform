package com.woosuk.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.woosuk.domain.model.ErrorState
import com.woosuk.domain.model.match.QueueType
import com.woosuk.domain.model.match.UserMatchInfo
import com.woosuk.domain.pagingsource.UserMatchInfoPagingSource
import com.woosuk.domain.repository.MatchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

class GetUserMatchInfoListUseCase(
    private val matchRepository: MatchRepository,
) {
    operator fun invoke(
        loadSize: Int,
        startTime: LocalDateTime? = null,
        endTime: LocalDateTime? = null,
        puuid: String,
        queueType: QueueType? = null,
        onError: (ErrorState) -> Unit,
    ): Flow<PagingData<UserMatchInfo>> {
        return Pager(
            config = PagingConfig(pageSize = loadSize, enablePlaceholders = true),
            pagingSourceFactory = {
                UserMatchInfoPagingSource(
                    matchRepository = matchRepository,
                    loadSize = loadSize,
                    startTime = startTime,
                    endTime = endTime,
                    puuid = puuid,
                    queueType = queueType,
                    onError = onError,
                )
            },
        ).flow
    }
}
