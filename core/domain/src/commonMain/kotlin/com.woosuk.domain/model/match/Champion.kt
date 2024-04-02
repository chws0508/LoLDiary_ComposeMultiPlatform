package com.woosuk.domain.model.match

import com.woosuk.domain.Url

data class Champion(
    val id: Int,
    val name: String,
) {
    val imageUrl = Url.cdnPrefix + CHAMPION_IMAGE_PREFIX + name + IMAGE_TYPE

    companion object {
        private const val CHAMPION_IMAGE_PREFIX =
            "img/champion/"
        private const val IMAGE_TYPE = ".png"
    }
}
