import extensions.configureIos

plugins {
    id("convention.android")
}

android { namespace = "com.woosuk.data" }

kotlin {
    configureIos("data")

    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(libs.skydoves.sandwich)
            implementation(project(":core:network"))
            implementation(project(":core:domain"))
            implementation(project(":core:database"))
        }
        iosMain.dependencies {
        }
    }
}
