package com.woosuk.domain.model.match

data class MatchDetails(
    val gameInfo: GameInfo,
    val currentUserPuuid: String,
    private val userMatchInfoList: List<UserMatchInfo>,
) {
    val currentUserMatch: UserMatchInfo =
        userMatchInfoList.find { it.account.puuid == currentUserPuuid }
            ?: throw IllegalStateException("현재 유저 경기 기록을 가져올 수 없어요")

    val blueTeamMatches: TeamMatches =
        TeamMatches(
            UserMatchInfoList(
                userMatchInfoList.filter {
                    it.team == TeamColor.Blue
                },
            ),
        )
    val redTeamMatches: TeamMatches =
        TeamMatches(
            UserMatchInfoList(
                userMatchInfoList.filter {
                    it.team == TeamColor.Red
                },
            ),
        )

    val currentUserTeamMatches: TeamMatches =
        if (blueTeamMatches.matches.contains(currentUserMatch)
        ) {
            blueTeamMatches
        } else {
            redTeamMatches
        }

    val otherTeamMatches: TeamMatches =
        if (currentUserTeamMatches.team == TeamColor.Blue) {
            redTeamMatches
        } else {
            blueTeamMatches
        }
}
