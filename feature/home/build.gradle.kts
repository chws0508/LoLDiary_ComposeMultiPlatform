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
}
multiplatformResources {
    multiplatformResourcesPackage = "com.woosuk.home"
}
