package com.woosuk.network.model

import kotlinx.serialization.Serializable

@Serializable
data class RuneCategoryDto(
    var id: Int,
    var key: String,
    var icon: String,
    var name: String,
    var slots: List<Slot>,
)

@Serializable
data class Slot(
    var runes: List<RuneDto>,
)

@Serializable
data class RuneDto(
    var id: Int,
    var key: String,
    var icon: String,
    var name: String,
    var shortDesc: String,
    var longDesc: String,
)
