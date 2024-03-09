package com.woosuk.network.di

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin

actual fun getClient(
    action: HttpClientConfig<*>.() -> Unit,
): HttpClient = HttpClient(Darwin) {
    action()
}
