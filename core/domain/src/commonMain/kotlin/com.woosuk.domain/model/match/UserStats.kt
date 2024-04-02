package com.woosuk.domain.model.match

data class UserStats(
    val kill: Int,
    val death: Int,
    val assist: Int,
    val isWin: Boolean,
) {
    val kdaScore = (kill + assist).toFloat() / death.toFloat()
}
