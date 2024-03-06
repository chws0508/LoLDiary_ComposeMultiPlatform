plugins { id("convention.multiplatform")}

android {
    namespace = "com.woosuk.onboarding"
}

kotlin{
    sourceSets {
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(libs.voyager.navigator)
            implementation(libs.composeImageLoader)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.moko.mvvm)
            implementation(libs.ktor.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.koin.core)
            implementation(project(":core:domain"))
        }

        androidMain.dependencies {
            implementation(libs.androidx.appcompat)
            implementation(libs.compose.uitooling)
            implementation(libs.kotlinx.coroutines.android)
        }

        iosMain.dependencies {
        }
    }
}
