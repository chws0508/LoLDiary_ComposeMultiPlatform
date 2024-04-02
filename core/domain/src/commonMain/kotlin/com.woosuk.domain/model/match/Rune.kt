package com.woosuk.domain.model.match

import io.github.aakira.napier.Napier

data class Rune(
    val id: Int,
    val icon: String,
) {
    val imageUrl = RUNE_IMAGE_PREFIX + icon

    init {
        Napier.v(imageUrl, tag = "룬 사진")
    }

    companion object {
        private const val RUNE_IMAGE_PREFIX =
            "https://ddragon.leagueoflegends.com/cdn/img/"
    }
}

data class RuneSelection(
    val id: Int,
    val iconUrl: String,
)
