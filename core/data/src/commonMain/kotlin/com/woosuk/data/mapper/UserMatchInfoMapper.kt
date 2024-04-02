package com.woosuk.data.mapper

import com.woosuk.data.repository.RuneRepository
import com.woosuk.domain.model.Account
import com.woosuk.domain.model.date.Date
import com.woosuk.domain.model.match.Champion
import com.woosuk.domain.model.match.GameInfo
import com.woosuk.domain.model.match.Item
import com.woosuk.domain.model.match.QueueType
import com.woosuk.domain.model.match.Spell
import com.woosuk.domain.model.match.UserMatchInfo
import com.woosuk.network.model.MatchInfoDto
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun MatchInfoDto.toUserMatchInfo(puuid: String): UserMatchInfo {
    val user =
        info.participants.find { it.puuid == puuid }
            ?: throw IllegalStateException("경기에서 해당하는 유저를 찾을 수 없습니다.")

    return UserMatchInfo(
        account =
            Account(
                puuid = puuid,
                summonerId = user.summonerId,
                nickName = user.riotIdGameName,
                tag = user.riotIdTagline,
                isCurrentUser = false,
            ),
        champion = Champion(id = user.championId, name = user.championName),
        items =
            listOf(
                Item(user.item0),
                Item(user.item1),
                Item(user.item2),
                Item(user.item3),
                Item(user.item4),
                Item(user.item5),
                Item(user.item6),
            ),
        spells =
            listOf(
                Spell.fromId(user.summoner1Id),
                Spell.fromId(user.summoner2Id),
            ),
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
                isWin = user.win,
            ),
        runes =
            listOf(
                RuneRepository.getRune(user.perks.styles[0].selections[0].perk), // 메인 룬
                RuneRepository.getRune(user.perks.styles[1].style), // 서브 룬
            ),
    )
}
