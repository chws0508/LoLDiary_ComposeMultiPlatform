package com.woosuk.data.mapper

import com.woosuk.domain.model.date.Date
import com.woosuk.domain.model.match.GameInfo
import com.woosuk.domain.model.match.MatchDetails
import com.woosuk.domain.model.match.QueueType
import com.woosuk.network.model.MatchInfoDto
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun MatchInfoDto.toMatchDetail(puuid: String): MatchDetails {
    val matchInfoList =
        info.participants.map {
            toUserMatchInfo(it.puuid)
        }
    return MatchDetails(
        currentUserPuuid = puuid,
        userMatchInfoList = matchInfoList,
        gameInfo =
            GameInfo(
                matchId = metadata.matchId,
                queueType = QueueType.from(info.queueId),
                totalPlayTime = info.gameDuration,
                startAt =
                    Date(
                        date =
                            Instant.fromEpochMilliseconds(info.gameStartTimestamp).toLocalDateTime(
                                TimeZone.currentSystemDefault(),
                            ),
                    ),
                endAt =
                    Date(
                        date =
                            Instant.fromEpochMilliseconds(info.gameEndTimestamp).toLocalDateTime(
                                TimeZone.currentSystemDefault(),
                            ),
                    ),
            ),
    )
}
