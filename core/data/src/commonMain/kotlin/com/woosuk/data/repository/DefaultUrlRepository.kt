package com.woosuk.data.repository

import com.woosuk.domain.repository.UrlRepository
import com.woosuk.network.service.CdnService
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update

class DefaultUrlRepository(
    private val cdnService: CdnService,
    private val coroutineDispatcher: CoroutineDispatcher,
) : UrlRepository {
    private val _cdnUrlPrefix = MutableStateFlow("")
    override val cdnUrlPrefix = _cdnUrlPrefix.asStateFlow()

    override suspend fun updateCdnUrlPrefix() =
        flow {
            val urlPrefix = cdnService.getCdnUrlPrefix()
            _cdnUrlPrefix.update { urlPrefix }
            emit(urlPrefix)
        }.catch {
            Napier.v(it.message.toString(), tag = "wooseok")
        }.flowOn(coroutineDispatcher)
}
