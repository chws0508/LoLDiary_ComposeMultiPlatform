import extensions.configureIos

plugins {
    id("convention.composeApp")
    id("dev.icerock.mobile.multiplatform-resources")
}
dependencies {
    commonMainApi(libs.bundles.moko.resources)
}
kotlin {
    configureIos(baseName = "composeApp")

    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.core.splashscreen)
            implementation(project(":core:designsystem"))
            implementation(project(":core:network"))
            implementation(project(":core:data"))
            implementation(project(":core:domain"))
            implementation(project(":core:navigation"))
            implementation(project(":feature:onboarding"))
            implementation(project(":feature:home"))
            implementation(project(":core:database"))
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
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}
multiplatformResources {
    multiplatformResourcesPackage = "com.woosuk.app"
}
