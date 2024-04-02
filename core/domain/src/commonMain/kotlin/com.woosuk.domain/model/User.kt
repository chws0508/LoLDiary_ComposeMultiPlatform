package com.woosuk.domain.model

import com.woosuk.domain.Url
import com.woosuk.domain.model.match.QueueType
import com.woosuk.domain.model.match.RankInfo

data class User(
    val account: Account,
    val rankInfoList: List<RankInfo>,
    val profileIconId: Int,
    val level: Int,
) {
    val profileImageUrl = Url.cdnPrefix + PROFILE_ICON_PREFIX + profileIconId + IMAGE_TYPE

    val freeRankInfo = rankInfoList.find { it.queueType == QueueType.FREE_RANK }
    val soloRankInfo = rankInfoList.find { it.queueType == QueueType.SOLO_RANK }

    companion object {
        private const val PROFILE_ICON_PREFIX =
            "img/profileicon/"

        private const val IMAGE_TYPE = ".png"
    }
}
