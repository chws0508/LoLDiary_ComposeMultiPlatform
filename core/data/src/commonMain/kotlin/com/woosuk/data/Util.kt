package com.woosuk.data

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ktor.statusCode
import com.skydoves.sandwich.messageOrNull
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.woosuk.domain.model.ErrorState

suspend fun <T> ApiResponse<T>.handleError(onError: suspend (ErrorState) -> Unit) {
    suspendOnError {
        onError(ErrorState(statusCode.code, messageOrNull))
    }.suspendOnException {
        onError(ErrorState(ErrorState.ExceptionCode, messageOrNull))
    }
}
