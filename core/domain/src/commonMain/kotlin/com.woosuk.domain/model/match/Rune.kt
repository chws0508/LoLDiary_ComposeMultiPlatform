package com.woosuk.domain.model.match

data class Rune(
    val id: Int,
    val icon: String,
) {
    val imageUrl = RUNE_IMAGE_PREFIX + icon

    companion object {
        private const val RUNE_IMAGE_PREFIX =
            "https://ddragon.leagueoflegends.com/cdn/img/"
    }
}

data class RuneSelection(
    val id: Int,
    val iconUrl: String,
)
