package com.woosuk.network.di

import LoLDiary.core.network.BuildConfig
import com.woosuk.network.service.Server
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {
    single<Json> {
        Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            coerceInputValues = true
            encodeDefaults = true
            isLenient = true
        }
    }

    single<HttpClient>(named("Kr")) {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    json = get(),
                    contentType = ContentType.Any
                )
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            defaultRequest {
                host = Server.KR.url
                header(
                    "X-Riot-Token",
                    BuildConfig.X_Riot_Token
                )
            }
            install(HttpTimeout) { requestTimeoutMillis = TIME_OUT }
        }
    }

    single<HttpClient>(named("Asia")) {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    json = get(),
                    contentType = ContentType.Any
                )
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            defaultRequest {
                host = Server.ASIA.url
                header(
                    "X-Riot-Token",
                    BuildConfig.X_Riot_Token
                )
            }
            install(HttpTimeout) { requestTimeoutMillis = TIME_OUT }
        }
    }
}

val serviceModule = module {
    includes(networkModule)
}

private const val TIME_OUT = 3000L
