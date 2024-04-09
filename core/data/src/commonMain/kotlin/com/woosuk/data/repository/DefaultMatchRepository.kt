package com.woosuk.data.repository

import com.skydoves.sandwich.getOrThrow
import com.skydoves.sandwich.suspendMapSuccess
import com.woosuk.data.handleError
import com.woosuk.data.mapper.toMatchDetail
import com.woosuk.data.mapper.toUserMatchInfo
import com.woosuk.domain.model.ErrorState
import com.woosuk.domain.model.match.MatchDetails
import com.woosuk.domain.model.match.UserMatchInfo
import com.woosuk.domain.repository.MatchRepository
import com.woosuk.network.service.MatchService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class DefaultMatchRepository(
    private val matchService: MatchService,
    private val coroutineDispatcher: CoroutineDispatcher,
) : MatchRepository {
    override fun getUserMatchInfos(
        offset: Int,
        loadSize: Int,
        queueId: Int?,
        startTime: LocalDateTime?,
        entTime: LocalDateTime?,
        puuid: String,
        onError: (ErrorState) -> Unit,
    ): Flow<List<UserMatchInfo>> =
        flow {
            val matchIdList =
                matchService.getMatchIdList(
                    offset = offset,
                    loadSize = loadSize,
                    queueId = queueId,
                    startTime = startTime?.toInstant(TimeZone.UTC)?.epochSeconds,
                    entTime = entTime?.toInstant(TimeZone.UTC)?.epochSeconds,
                    puuid = puuid,
                ).getOrThrow()

            val userMatchInfoList =
                withContext(Dispatchers.IO) {
                    matchIdList.map {
                        async {
                            matchService.getMatchInfo(it).getOrThrow().toUserMatchInfo(puuid)
                        }
                    }
                }
            emit(userMatchInfoList.awaitAll())
        }.catch {
            println("wooseok" + it.message.toString())
            onError(ErrorState(null, it.message))
        }.flowOn(coroutineDispatcher)

    override fun getUserMatchDetail(
        matchId: String,
        puuid: String,
        onError: suspend (ErrorState) -> Unit,
    ): Flow<MatchDetails> =
        flow {
            matchService.getMatchInfo(matchId)
                .suspendMapSuccess {
                    emit(this.toMatchDetail(puuid))
                }.handleError(onError)
        }.flowOn(coroutineDispatcher)
}
