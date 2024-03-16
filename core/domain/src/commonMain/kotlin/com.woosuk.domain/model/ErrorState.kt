package com.woosuk.domain.model

data class ErrorState(
    val errorCode: Int?,
    val errorMessage: String?,
) {
    companion object {
        const val ExceptionCode = -1
    }
}
