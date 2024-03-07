package com.woosuk.domain.di

import com.woosuk.domain.usecase.LoginUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single<LoginUseCase> { LoginUseCase(get()) }
}
