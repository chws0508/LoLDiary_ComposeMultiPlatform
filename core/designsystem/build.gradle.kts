import extensions.configureIos

plugins { id("convention.android") }

android { namespace = "com.woosuk.designsystem" }
dependencies {
    implementation(libs.androidx.core)
}

kotlin {
    configureIos("designsystem")

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activityCompose)
            implementation(libs.androidx.appcompat)
        }
        commonMain.dependencies {
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.resources)
        }
        iosMain.dependencies {
        }
    }
}
