package com.woosuk.data.di

import com.woosuk.data.repository.DefaultUserRepository
import com.woosuk.domain.repository.UserRepository
import com.woosuk.network.di.serviceModule
import org.koin.dsl.module

val repositoryModule = module {
    includes(serviceModule)
    single<UserRepository> { DefaultUserRepository(get()) }
}
