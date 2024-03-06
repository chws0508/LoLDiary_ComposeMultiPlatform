package com.woosuk.network.model

import kotlinx.serialization.Serializable

@Serializable
data class UserUidDto(
    val puuid: String,
    val gameName: String,
    val tagLine: String,
)
