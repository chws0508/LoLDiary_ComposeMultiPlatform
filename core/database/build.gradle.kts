import extensions.configureIos

plugins {
    id("convention.android")
    alias(libs.plugins.sqlDelight)
}

android { namespace = "com.woosuk.database" }

kotlin {
    configureIos("database")

    sourceSets {
        androidMain.dependencies {
            implementation(libs.sqlDelight.driver.android)
        }
        commonMain.dependencies {
            implementation(libs.sqlDelight.coroutine)
            implementation(libs.sqlDelight.runtime)
        }
        iosMain.dependencies {
            implementation(libs.sqlDelight.driver.native)
            implementation(libs.sqliter)
        }
    }
}

sqldelight {
    databases {
        create("LoLDiaryDatabase") {
            packageName.set("com.woosuk.database")
        }
        linkSqlite.set(true)
    }
}
