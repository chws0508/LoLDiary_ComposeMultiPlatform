package com.woosuk.domain.di

import com.woosuk.domain.usecase.GetCurrentAccountUseCase
import com.woosuk.domain.usecase.GetCurrentUserUseCase
import com.woosuk.domain.usecase.GetRecentAccountsUseCase
import com.woosuk.domain.usecase.LoginUseCase
import org.koin.dsl.module

val useCaseModule =
    module {
        single<LoginUseCase> { LoginUseCase(get()) }
        single { GetRecentAccountsUseCase(get()) }
        single { GetCurrentAccountUseCase(get()) }
        single { GetCurrentUserUseCase(get()) }
    }
