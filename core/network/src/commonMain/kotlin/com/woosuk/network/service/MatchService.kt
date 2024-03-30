package com.woosuk.network.service

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.requestApiResponse
import com.woosuk.network.model.MatchInfoDto
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments

interface MatchService {
    suspend fun getMatchIdList(
        offset: Int,
        loadSize: Int,
        queueId: Int?,
        startTime: Long?,
        entTime: Long?,
        puuid: String,
    ): ApiResponse<List<String>>

    suspend fun getMatchInfo(matchId: String): ApiResponse<MatchInfoDto>
}

class DefaultMatchService(
    private val asiaHttpClient: HttpClient,
) : MatchService {
    override suspend fun getMatchIdList(
        offset: Int,
        loadSize: Int,
        queueId: Int?,
        startTime: Long?,
        entTime: Long?,
        puuid: String,
    ): ApiResponse<List<String>> {
        return asiaHttpClient.requestApiResponse<List<String>>(
            "lol/match/v5/matches/by-puuid",
        ) {
            url {
                protocol = URLProtocol.HTTPS
                appendPathSegments(puuid, "ids")
                startTime?.let { parameter("startTime", it) }
                entTime?.let { parameter("endTime", it) }
                queueId?.let { parameter("queue", it) }
                parameter("count", loadSize)
                parameter("start", offset)
            }
        }
    }

    override suspend fun getMatchInfo(matchId: String): ApiResponse<MatchInfoDto> {
        return asiaHttpClient.requestApiResponse<MatchInfoDto>(
            "lol/match/v5/matches",
        ) {
            url {
                protocol = URLProtocol.HTTPS
                appendPathSegments(matchId)
            }
        }
    }
}
