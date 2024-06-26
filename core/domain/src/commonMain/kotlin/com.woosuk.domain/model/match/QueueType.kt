package com.woosuk.domain.model.match

enum class QueueType(val typeName: String, val id: Int) {
    SOLO_RANK("RANKED_SOLO_5x5", 420),
    NORMAL("", 490),
    URF("", 1900),
    FREE_RANK("RANKED_FLEX_SR", 440),
    RANDOM("", 450),
    ;

    companion object {
        fun from(id: Int): QueueType {
            return entries.firstOrNull { it.id == id } ?: throw IllegalStateException("일치하는 Queue ID가 없습니다")
        }
    }
}
