package com.woosuk.network.service

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.getApiResponse
import com.skydoves.sandwich.ktor.requestApiResponse
import com.woosuk.network.model.UserAccountDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments

interface UserService {
    suspend fun getUserAccount(
        gameName: String,
        tagLine: String,
    ): ApiResponse<UserAccountDto>
}

class DefaultUserService(
    private val httpClient: HttpClient,
) : UserService {

    override suspend fun getUserAccount(
        gameName: String,
        tagLine: String,
    ): ApiResponse<UserAccountDto> {
        return httpClient.requestApiResponse<UserAccountDto>(
            "riot/account/v1/accounts/by-riot-id"
        ){
            url{
                protocol = URLProtocol.HTTPS
                appendPathSegments(gameName, tagLine)
            }
        }
    }
}
