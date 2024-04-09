package com.woosuk.domain.model.match

import io.github.aakira.napier.Napier

data class TeamMatches(
    private val initList: UserMatchInfoList,
) {
    init {
        Napier.v { initList.list.toString() }
        require(initList.list.all { initList.list[0].team == it.team })
    }

    val isWin = initList.list[0].userStats.isWin

    val team = initList.list[0].team

    val totalKill = initList.totalKill

    val totalAssist = initList.totalAssist

    val totalDeath = initList.totalDeath

    val matches: List<UserMatchInfo>
        get() {
            val result = mutableListOf<UserMatchInfo>()
            Lane.entries.forEach { lane ->
                result.add(initList.list.firstOrNull { it.lane == lane } ?: return initList.list)
            }
            return result
        }
}
