package com.woosuk.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UrlRepository {
    val cdnUrlPrefix: StateFlow<String>

    suspend fun updateCdnUrlPrefix(): Flow<String>
}
