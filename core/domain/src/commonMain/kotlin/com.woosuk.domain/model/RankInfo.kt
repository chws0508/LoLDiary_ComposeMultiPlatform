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

data class RankTier(
    val type: RankTierType,
    val step: RankTierStep,
    val point: Int,
)

enum class RankTierType {
    IRON,
    BRONZE,
    SILVER,
    GOLD,
    PLATINUM,
    EMERALD,
    DIAMOND,
    MASTER,
    GRANDMASTER,
    CHALLENGER,
    ;

    companion object {
        fun from(value: String): RankTierType = entries.find { it.name == value } ?: throw IllegalStateException("알 수 없는 티어가 나왔어요")
    }
}

enum class RankTierStep(val value: Int) {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    NONE(0),
    ;

    companion object {
        fun from(value: String) =
            when (value) {
                "I" -> ONE
                "II" -> TWO
                "III" -> THREE
                "IV" -> FOUR
                else -> NONE
            }
    }
}
