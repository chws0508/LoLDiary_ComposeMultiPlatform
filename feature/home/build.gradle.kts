import extensions.configureIos

plugins {
    id("convention.feature")
    id("dev.icerock.mobile.multiplatform-resources")
}
dependencies {
    commonMainApi(libs.bundles.moko.resources)
}
android {
    namespace = "com.woosuk.home"
}
kotlin {
    configureIos("home")
    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.paging.compose)
            implementation("app.cash.paging:paging-common:3.3.0-alpha02-0.5.1")
        }
    }
}
multiplatformResources {
    multiplatformResourcesPackage = "com.woosuk.home"
}
