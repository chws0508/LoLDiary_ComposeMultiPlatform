import extensions.configureIos

plugins {
    id("convention.android")
    id("dev.icerock.mobile.multiplatform-resources")
}

dependencies {
    commonMainApi(libs.bundles.moko.resources)
}
android {
    namespace = "com.woosuk.ui"
}

kotlin {
    configureIos("ui")

    sourceSets {
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }
        androidMain.dependencies {
            implementation(libs.androidx.activityCompose)
            implementation(libs.androidx.appcompat)
        }
        commonMain.dependencies {
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.resources)
            implementation(project(":core:domain"))
        }
        iosMain.dependencies {
        }
    }
}
multiplatformResources {
    multiplatformResourcesPackage = "com.woosuk.ui"
}
