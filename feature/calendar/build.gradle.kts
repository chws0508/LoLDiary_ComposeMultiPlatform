import extensions.configureIos

plugins {
    id("convention.feature")
    id("dev.icerock.mobile.multiplatform-resources")
}
dependencies {
    commonMainApi(libs.bundles.moko.resources)
}
android {
    namespace = "com.woosuk.calendar"
}
kotlin {
    configureIos("calendar")
}
multiplatformResources {
    multiplatformResourcesPackage = "com.woosuk.calendar"
}
