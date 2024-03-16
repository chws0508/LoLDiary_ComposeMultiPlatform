package com.woosuk.data.di

import com.woosuk.data.repository.DefaultAccountRepository
import com.woosuk.data.repository.DefaultUserRepository
import com.woosuk.domain.repository.AccountRepository
import com.woosuk.domain.repository.UserRepository
import com.woosuk.network.di.serviceModule
import org.koin.dsl.module

val repositoryModule =
    module {
        includes(serviceModule)
        single<AccountRepository> { DefaultAccountRepository(get(), get()) }
        single<UserRepository> { DefaultUserRepository(get(), get()) }
    }
