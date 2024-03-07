package com.woosuk.data.mapper

import com.woosuk.domain.model.UserAccount
import com.woosuk.network.model.UserAccountDto

fun UserAccountDto.toDomain() = UserAccount(
    puUid = puuid,
    gameName = gameName,
    tagLine = tagLine
)
