package com.woosuk.network.model

import kotlinx.serialization.Serializable

@Serializable
data class UserAccountDto(
    val puuid: String,
    val gameName: String,
    val tagLine: String,
)
