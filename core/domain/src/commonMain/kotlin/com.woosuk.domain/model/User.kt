package com.woosuk.domain.model

import com.woosuk.domain.Url
import com.woosuk.domain.model.match.RankInfo

data class User(
    val account: Account,
    val rankInfo: RankInfo,
    val profileIconId: Int,
    val level: Int,
) {
    val profileImageUrl = Url.cdnPrefix + PROFILE_ICON_PREFIX + profileIconId + IMAGE_TYPE

    companion object {
        private const val PROFILE_ICON_PREFIX =
            "img/profileicon/"

        private const val IMAGE_TYPE = ".png"
    }
}
