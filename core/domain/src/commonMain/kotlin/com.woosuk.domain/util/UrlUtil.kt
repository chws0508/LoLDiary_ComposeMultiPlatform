package com.woosuk.domain.util

import com.woosuk.domain.repository.UrlRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object UrlUtil : KoinComponent {
    private val urlRepository: UrlRepository by inject()

    val cdnPrefix: String
        get() = urlRepository.cdnUrlPrefix.value
}
