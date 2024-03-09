package com.woosuk.database

import org.koin.core.module.Module
import org.koin.dsl.module

expect fun sqlDriverModule(): Module

val databaseModule = module {
    includes(sqlDriverModule())
    single<LoLDiaryDatabase> { LoLDiaryDatabase(driver = get()) }
    single<UserQueries> { UserQueries(get()) }
}

val dataSourceModule = module {
    includes(databaseModule)
    single<UserDatabaseDataSource> { DefaultUserDatabaseResource(get()) }
}
