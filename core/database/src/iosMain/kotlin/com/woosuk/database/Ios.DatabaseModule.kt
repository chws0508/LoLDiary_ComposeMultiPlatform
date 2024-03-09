package com.woosuk.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun sqlDriverModule(): Module = module {
    single<SqlDriver> {
        NativeSqliteDriver(LoLDiaryDatabase.Schema, "loldiary.db")
    }
}
