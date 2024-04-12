package com.woosuk.domain.di

import com.woosuk.domain.usecase.GetCurrentAccountUseCase
import com.woosuk.domain.usecase.GetCurrentUserUseCase
import com.woosuk.domain.usecase.GetMatchDetailsUseCase
import com.woosuk.domain.usecase.GetRecentAccountsUseCase
import com.woosuk.domain.usecase.GetUserMatchInfoListByDateUseCase
import com.woosuk.domain.usecase.GetUserMatchInfoPagingUseCase
import com.woosuk.domain.usecase.LogOutUseCase
import com.woosuk.domain.usecase.LoginUseCase
import org.koin.dsl.module

val useCaseModule =
    module {
        single<LoginUseCase> { LoginUseCase(get()) }
        single { GetRecentAccountsUseCase(get()) }
        single { GetCurrentAccountUseCase(get()) }
        single { GetCurrentUserUseCase(get()) }
        single { GetUserMatchInfoPagingUseCase(get()) }
        single { GetUserMatchInfoListByDateUseCase(get()) }
        single { GetMatchDetailsUseCase(get(), get()) }
        single { LogOutUseCase(get()) }
    }
