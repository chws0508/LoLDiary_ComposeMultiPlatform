import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import extensions.configureIos

plugins {
    id("convention.android")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.buildConfig)
}

android { namespace = "com.woosuk.network" }

kotlin {
   configureIos("network")

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.core)
            implementation(libs.ktor.content.negotiation)
            implementation(libs.ktor.json)
            implementation(libs.ktor.logging)
            implementation(libs.skydoves.sandwich)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

buildConfig {
    buildConfigField<String>(
        "X_Riot_Token",
        gradleLocalProperties(rootDir).getProperty("X_RIOT_TOKEN")
    )
}
