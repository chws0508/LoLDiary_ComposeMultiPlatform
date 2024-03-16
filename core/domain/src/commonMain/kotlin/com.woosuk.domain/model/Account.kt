package com.woosuk.domain.model

data class Account(
    val puuid: String,
    val summonerId: String,
    val nickName: String,
    val tag: String,
    val isCurrentUser: Boolean,
) {
    override fun toString(): String = nickName + tag
}
