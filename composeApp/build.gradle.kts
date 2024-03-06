import extensions.configureIos

plugins { id("convention.composeApp") }

kotlin {
    configureIos(baseName = "composeApp")

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:domain"))
            implementation(project(":feature:onboarding"))
        }
    }
}

android {
    namespace = "com.woosuk.app"

    defaultConfig {
        targetSdk = 34
        applicationId = "com.woosuk.app.androidApp"
        versionCode = 1
        versionName = "1.0.0"
    }
}

//buildConfig {
//    // BuildConfig configuration here.
//    // https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
//}

//sqldelight {
//    databases {
//        create("MyDatabase") {
//            // Database configuration here.
//            // https://cashapp.github.io/sqldelight
//            packageName.set("com.woosuk.app.db")
//        }
//    }
//}
