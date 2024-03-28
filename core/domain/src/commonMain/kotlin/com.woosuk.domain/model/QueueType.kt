package com.woosuk.domain.model

enum class QueueType(val value: String, val id: Int) {
    SOLO_RANK("RANKED_SOLO_5x5", 420),
    NORMAL("", 490),
    URF("", 1900),
    FREE_RANK("", 440),
    RANDOM("", 450),
}
