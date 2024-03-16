import extensions.configureIos

plugins { id("convention.android") }

android { namespace = "com.woosuk.navigation" }

kotlin {
    configureIos("navigation")

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:designsystem"))
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.screenModel)
            implementation(libs.voyager.koin)
            implementation(libs.voyager.tabNavigator)
        }
        iosMain.dependencies {  }
    }
}
