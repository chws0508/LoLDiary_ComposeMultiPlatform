package com.woosuk.domain.model.match

class UserMatchInfoList(
    initValue: List<UserMatchInfo>,
) {
    val list = initValue.toList()

    val totalWins =
        initValue.fold(0) { acc, userMatchInfo ->
            if (userMatchInfo.userStats.isWin) {
                acc + 1
            } else {
                acc
            }
        }

    val totalLosses = initValue.count() - totalWins

    val winRate =
        when {
            totalLosses == 0 && totalWins == 0 -> 0.0F
            totalLosses == 0 -> 100.0F
            else -> totalWins.toFloat() * 100 / list.size
        }
}
