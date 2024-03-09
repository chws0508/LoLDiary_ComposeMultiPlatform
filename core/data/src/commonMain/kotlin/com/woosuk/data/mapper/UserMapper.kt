package com.woosuk.data.mapper

import com.woosuk.database.UserAccountEntity
import com.woosuk.domain.model.UserAccount
import com.woosuk.network.model.UserAccountDto

fun UserAccountDto.toDomain() = UserAccount(
    puuid = puuid,
    nickName = gameName,
    tag = tagLine,
    isCurrentUser = false,
)

fun UserAccountEntity.toDomain() = UserAccount(
    puuid = puuid,
    nickName = nick_name,
    tag = tag,
    isCurrentUser = if (is_current_user == 1L) true else false
)

fun UserAccount.toEntity() = UserAccountEntity(
    puuid = puuid,
    nick_name = nickName,
    tag = tag,
    is_current_user = if (isCurrentUser) 1L else -1
)
