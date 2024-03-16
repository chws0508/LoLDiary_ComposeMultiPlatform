package com.woosuk.data.mapper

import com.woosuk.database.AccountEntity
import com.woosuk.domain.model.Account


fun AccountEntity.toAccount() = Account(
    puuid = puuid,
    nickName = nick_name,
    tag = tag,
    summonerId = summoner_id,
    isCurrentUser = if (is_current_user == 1L) true else false
)

fun Account.toAccountEntity(isCurrentUser: Boolean) = AccountEntity(
    puuid = puuid,
    summoner_id = summonerId,
    nick_name = nickName,
    tag = tag,
    is_current_user = if (isCurrentUser) 1L else -1L
)
