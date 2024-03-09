package com.woosuk.domain.model

data class UserAccount(
    val puuid: String,
    val nickName: String,
    val tag: String,
    val isCurrentUser: Boolean,
)
