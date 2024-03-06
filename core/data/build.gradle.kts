import extensions.configureIos

plugins {
    id("convention.android")
}

android { namespace = "com.woosuk.data" }

kotlin {
    configureIos("data")

    sourceSets {
        androidMain.dependencies {

        }
        commonMain.dependencies {
            implementation(libs.skydoves.sandwich)
            implementation(project(":core:network"))
        }
        iosMain.dependencies {
        }
    }
}
