package com.woosuk.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun sqlDriverModule(): Module = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = LoLDiaryDatabase.Schema,
            context = get<Context>(),
            name = "loldiary.db",
        )
    }
}
