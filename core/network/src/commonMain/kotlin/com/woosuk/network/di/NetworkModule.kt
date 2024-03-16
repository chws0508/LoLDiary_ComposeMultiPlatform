package com.woosuk.network.di

import LoLDiary.core.network.BuildConfig
import com.woosuk.network.Server
import com.woosuk.network.service.AccountService
import com.woosuk.network.service.DefaultAccountService
import com.woosuk.network.service.DefaultUserService
import com.woosuk.network.service.UserService
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
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

expect fun getClient(action: HttpClientConfig<*>.() -> Unit): HttpClient

val networkModule =
    module {
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
            getClient {
                install(ContentNegotiation) {
                    json(
                        json = get(),
                        contentType = ContentType.Any,
                    )
                }
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.ALL
                }
                defaultRequest {
                    url(Server.KR.url)
                    header("X-Riot-Token", BuildConfig.X_Riot_Token)
                }
                install(HttpTimeout) { requestTimeoutMillis = TIME_OUT }
            }
        }

        single<HttpClient>(named("Asia")) {
            getClient {
                install(ContentNegotiation) {
                    json(
                        json = get(),
                        contentType = ContentType.Any,
                    )
                }
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.ALL
                }
                defaultRequest {
                    url(Server.ASIA.url)
                    header("X-Riot-Token", BuildConfig.X_Riot_Token)
                }
                install(HttpTimeout) { requestTimeoutMillis = TIME_OUT }
            }
        }
    }

val serviceModule =
    module {
        includes(networkModule)
        single<AccountService> {
            DefaultAccountService(
                get(named("Asia")),
                get(named("Kr")),
            )
        }
        single<UserService> {
            DefaultUserService(
                get(named("Kr")),
            )
        }
    }

private const val TIME_OUT = 3000L
