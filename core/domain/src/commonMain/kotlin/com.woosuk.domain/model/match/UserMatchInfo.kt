package com.woosuk.domain.model.match

import com.woosuk.domain.model.Account

data class UserMatchInfo(
    val account: Account,
    val champion: Champion,
    val items: List<Item>,
    val spells: List<Spell>,
    val runes: List<Rune>,
    val gameInfo: GameInfo,
    val userStats: UserStats,
)
