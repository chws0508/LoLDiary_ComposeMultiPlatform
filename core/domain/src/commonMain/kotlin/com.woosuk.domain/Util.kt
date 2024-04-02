package com.woosuk.domain

import com.woosuk.domain.repository.UrlRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object Url : KoinComponent {
    private val urlRepository: UrlRepository by inject()

    val cdnPrefix: String
        get() = urlRepository.cdnUrlPrefix.value
}
