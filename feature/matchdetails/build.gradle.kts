import extensions.configureIos

plugins {
    id("convention.feature")
    id("dev.icerock.mobile.multiplatform-resources")
}
dependencies {
    commonMainApi(libs.bundles.moko.resources)
}
android {
    namespace = "com.woosuk.matchdetails"
}
kotlin {
    configureIos("matchdetails")
}
multiplatformResources {
    multiplatformResourcesPackage = "com.woosuk.matchdetails"
}
