package com.woosuk.network.service

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.getApiResponse
import com.woosuk.network.model.UserAccountDto
import io.ktor.client.HttpClient

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
        return httpClient.getApiResponse<UserAccountDto>(
            "riot/account/v1/accounts/by-riot-id/$gameName/$tagLine"
        )
    }
}
