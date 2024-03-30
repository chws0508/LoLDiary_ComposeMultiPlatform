package com.woosuk.domain.model

import com.woosuk.domain.model.date.Date

data class UserMatchInfo(
    val account: Account,
    val champion: Champion,
    val item: List<Item>,
    val gameInfo: GameInfo,
)

data class Champion(
    val id: Int,
    val name: String,
) {
    val imageUrl = CHAMPION_IMAGE_PREFIX + name + IMAGE_TYPE

    companion object {
        private const val CHAMPION_IMAGE_PREFIX =
            "https://ddragon.leagueoflegends.com/cdn/13.24.1/img/champion/"
        private const val IMAGE_TYPE = ".png"
    }
}

data class Item(
    val id: Int,
) {
    val imageUrl = ITEM_IMAGE_PREFIX + id + IMAGE_TYPE

    companion object {
        private const val ITEM_IMAGE_PREFIX =
            "https://ddragon.leagueoflegends.com/cdn/13.24.1/img/item/"
        private const val IMAGE_TYPE = ".png"
    }
}

data class GameInfo(
    val matchId: String,
    val queueType: QueueType,
    val totalPlayTime: Long,
    val startAt: Date,
    val endAt: Date,
    val isWin: Boolean,
)
