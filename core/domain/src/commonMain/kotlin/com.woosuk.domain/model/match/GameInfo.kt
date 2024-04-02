package com.woosuk.domain.model.match

import com.woosuk.domain.model.date.Date

data class GameInfo(
    val matchId: String,
    val queueType: QueueType,
    val totalPlayTime: Long,
    val startAt: Date,
    val endAt: Date,
    val isWin: Boolean,
)
