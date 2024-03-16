package com.woosuk.domain.model

data class RankInfo(
    val queueType: QueueType,
    val rankTier: RankTier,
    val winCount: Int,
    val loseCount: Int,
) {
    fun getWinRate(): Float {
        return (winCount / (winCount + loseCount).toFloat()) * 100
    }
}

enum class QueueType(value: String) {
    SOLO_RANK("RANKED_SOLO_5x5")
}

data class RankTier(
    val type: RankTierType,
    val step: RankTierStep,
    val point: Int,
)

enum class RankTierType {
    IRON, BRONZE, SILVER, GOLD, PLATINUM, DIAMOND, MASTER, GRANDMASTER, CHALLENGER
}

enum class RankTierStep(val value: Int) {
    ONE(1), TWO(2), THREE(3), FOUR(4), NONE(0)
}
