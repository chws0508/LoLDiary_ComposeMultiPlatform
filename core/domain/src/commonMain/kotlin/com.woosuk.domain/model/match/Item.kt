package com.woosuk.domain.model.match

import com.woosuk.domain.Url

data class Item(
    val id: Int,
) {
    val imageUrl = Url.cdnPrefix + ITEM_IMAGE_PREFIX + id + IMAGE_TYPE

    companion object {
        private const val ITEM_IMAGE_PREFIX =
            "img/item/"
        private const val IMAGE_TYPE = ".png"
    }
}
