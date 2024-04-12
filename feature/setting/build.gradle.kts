import extensions.configureIos

plugins {
    id("convention.feature")
    id("dev.icerock.mobile.multiplatform-resources")
}
dependencies {
    commonMainApi(libs.bundles.moko.resources)
}
android {
    namespace = "com.woosuk.setting"
}
kotlin {
    configureIos("home")
    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.paging.compose)
        }
    }
}
multiplatformResources {
    multiplatformResourcesPackage = "com.woosuk.setting"
}
