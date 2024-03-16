package com.woosuk.network.service

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.requestApiResponse
import com.woosuk.network.model.RankInfoDto
import io.ktor.client.HttpClient
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments

interface UserService {
    suspend fun gerRankInfo(summonerId: String): ApiResponse<List<RankInfoDto>>
}

class DefaultUserService(
    private val krHttpClient: HttpClient,
) : UserService {
    override suspend fun gerRankInfo(summonerId: String): ApiResponse<List<RankInfoDto>> {
        return krHttpClient.requestApiResponse<List<RankInfoDto>>(
            "lol/league/v4/entries/by-summoner",
        ) {
            url {
                protocol = URLProtocol.HTTPS
                appendPathSegments(summonerId)
            }
        }
    }
}
