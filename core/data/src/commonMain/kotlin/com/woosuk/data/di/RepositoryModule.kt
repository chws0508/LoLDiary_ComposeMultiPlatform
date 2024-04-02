package com.woosuk.data.di

import com.woosuk.data.repository.DefaultAccountRepository
import com.woosuk.data.repository.DefaultMatchRepository
import com.woosuk.data.repository.DefaultUrlRepository
import com.woosuk.data.repository.DefaultUserRepository
import com.woosuk.domain.repository.AccountRepository
import com.woosuk.domain.repository.MatchRepository
import com.woosuk.domain.repository.UrlRepository
import com.woosuk.domain.repository.UserRepository
import com.woosuk.network.di.serviceModule
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule =
    module {
        includes(serviceModule, dispatcherModule)
        single<AccountRepository> { DefaultAccountRepository(get(), get()) }
        single<UserRepository> { DefaultUserRepository(get(), get(), get()) }
        single<MatchRepository> { DefaultMatchRepository(get(), get(named("IO"))) }
        single<UrlRepository> { DefaultUrlRepository(get(), get(named("IO"))) }
    }
