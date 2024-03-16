package com.woosuk.network.service

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.getApiResponse
import com.skydoves.sandwich.ktor.requestApiResponse
import com.woosuk.network.model.PuuidDto
import com.woosuk.network.model.SummonerDto
import io.ktor.client.HttpClient
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments

interface AccountService {
    suspend fun getPuuid(
        gameName: String,
        tagLine: String,
    ): ApiResponse<PuuidDto>

    suspend fun getSummoner(puuid: String): ApiResponse<SummonerDto>
}

class DefaultAccountService(
    private val asiaHttpClient: HttpClient,
    private val krHttpClient: HttpClient,
) : AccountService {
    override suspend fun getPuuid(
        gameName: String,
        tagLine: String,
    ): ApiResponse<PuuidDto> =
        asiaHttpClient.requestApiResponse<PuuidDto>(
            "riot/account/v1/accounts/by-riot-id",
        ) {
            url {
                protocol = URLProtocol.HTTPS
                appendPathSegments(gameName, tagLine)
            }
        }

    override suspend fun getSummoner(puuid: String): ApiResponse<SummonerDto> =
        krHttpClient.getApiResponse<SummonerDto>(
            urlString = "lol/summoner/v4/summoners/by-puuid",
        ) {
            url {
                protocol = URLProtocol.HTTPS
                appendPathSegments(puuid)
            }
        }
}
