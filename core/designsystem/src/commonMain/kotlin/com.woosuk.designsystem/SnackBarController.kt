package com.woosuk.designsystem

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SnackBarController(
    private val coroutineScope: CoroutineScope,
    val snackBarHostState: SnackbarHostState
) {
    fun showMessage(
        message: String,
        actionLabel: String? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration = SnackbarDuration.Short,
        onSnackbarResult: (SnackbarResult) -> Unit = {}
    ) {
        coroutineScope.launch {
            snackBarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                withDismissAction = withDismissAction,
                duration = duration
            ).let(onSnackbarResult)
        }
    }
}

val LocalSnackbarController = compositionLocalOf<SnackBarController> {
    error("No Snackbar Controller")
}

