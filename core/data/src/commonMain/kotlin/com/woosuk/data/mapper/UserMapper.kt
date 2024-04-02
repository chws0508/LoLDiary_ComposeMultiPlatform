package com.woosuk.data.mapper

import com.woosuk.domain.model.match.QueueType
import com.woosuk.domain.model.match.RankInfo
import com.woosuk.domain.model.match.RankTier
import com.woosuk.domain.model.match.RankTierStep
import com.woosuk.domain.model.match.RankTierType
import com.woosuk.network.model.RankInfoDto

fun RankInfoDto.toRankInfo() =
    RankInfo(
        queueType =
            when (queueType) {
                QueueType.SOLO_RANK.value -> QueueType.SOLO_RANK
                else -> QueueType.SOLO_RANK
            },
        rankTier =
            RankTier(
                type = RankTierType.from(tier),
                step = RankTierStep.from(rank),
                point = leaguePoints,
            ),
        winCount = wins,
        loseCount = losses,
    )
