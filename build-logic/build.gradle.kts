plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("featurePlugin") {
            id = "convention.feature"
            implementationClass = "FeaturePlugin"
        }
        register("androidPlugin") {
            id = "convention.android.library"
            implementationClass = "AndroidPlugin"
        }
        register("applicationPlugin") {
            id = "convention.android.application"
            implementationClass = "ApplicationPlugin"
        }
        register("kotlinPlugin"){
            id = "convention.kotlin"
            implementationClass = "KotlinPlugin"
        }
    }
}
