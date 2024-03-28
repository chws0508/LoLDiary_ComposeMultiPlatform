package com.woosuk.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MatchDto(
    val metadata: MetaDataDto,
    val info: InfoDto,
)

@Serializable
data class MetaDataDto(
    val dataVersion: String,
    val matchId: String,
    val participants: List<String>,
)

@Serializable
data class InfoDto(
    val gameId: Long,
    val gameType: String,
    val gameDuration: Long,
    val gameEndTimestamp: Long,
    val gameStartTimestamp: Long,
    val participants: List<ParticipantDto>,
    val queueId: Int,
)

@Serializable
data class TeamDto(
    val win: Boolean,
    val teamId: Int,
    val participants: List<ParticipantDto>,
)

@Serializable
data class ParticipantDto(
    val puuid: String,
    val summonerId: String,
    val summonerName: String,
    val riotIdGameName: String,
    val riotIdTagline: String,
    val kills: Int,
    val assists: Int,
    val deaths: Int,
    val champLevel: Int,
    val championId: Int,
    val championName: String,
    val item0: Int,
    val item1: Int,
    val item2: Int,
    val item3: Int,
    val item4: Int,
    val item5: Int,
    val item6: Int,
    val summoner1Id: Int,
    val summoner2Id: Int,
    val win: Boolean,
    val teams: TeamDto,
)
