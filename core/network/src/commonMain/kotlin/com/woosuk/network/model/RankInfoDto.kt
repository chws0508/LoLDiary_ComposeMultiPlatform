package com.woosuk.network.model

import kotlinx.serialization.Serializable

@Serializable
data class RankInfoDto(
    val leagueId: String,
    val summonerId: String,
    val summonerName: String,
    val queueType: String,
    val tier: String,
    val rank: String,
    val leaguePoints: Int,
    val wins: Int,
    val losses: Int,
)
