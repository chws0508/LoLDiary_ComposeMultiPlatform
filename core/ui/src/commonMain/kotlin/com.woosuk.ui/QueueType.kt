package com.woosuk.ui

import com.woosuk.domain.model.match.QueueType

fun QueueType.getName(): String {
    return when (this) {
        QueueType.SOLO_RANK -> "솔로 랭크"
        QueueType.NORMAL -> "일반"
        QueueType.URF -> "URF"
        QueueType.FREE_RANK -> "자유 랭크"
        QueueType.RANDOM -> "무작위 총력전"
    }
}
