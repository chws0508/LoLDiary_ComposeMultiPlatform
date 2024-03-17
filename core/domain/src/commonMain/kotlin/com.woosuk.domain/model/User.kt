package com.woosuk.domain.model

data class User(
    val account: Account,
    val rankInfo: RankInfo,
    val profileIconId: Int,
    val level: Int,
) {
    val profileImageUrl = PROFILE_ICON_PREFIX + profileIconId + IMAGE_TYPE

    companion object {
        private const val PROFILE_ICON_PREFIX =
            "https://ddragon.leagueoflegends.com/cdn/13.24.1/img/profileicon/"

        private const val IMAGE_TYPE = ".png"
    }
}
