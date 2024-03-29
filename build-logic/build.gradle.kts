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
        register("composeAppPlugin"){
            id= "convention.composeApp"
            implementationClass = "ComposeAppPlugin"
        }
        register("featurePlugin") {
            id = "convention.feature"
            implementationClass = "FeaturePlugin"
        }
        register("kotlinPlugin"){
            id = "convention.kotlin"
            implementationClass = "KotlinPlugin"
        }
        register("androidPlugin") {
            id = "android.library"
            implementationClass = "AndroidPlugin"
        }
        register("applicationPlugin") {
            id = "application.library"
            implementationClass = "ApplicationPlugin"
        }
        register("kotlinAndroidPlugin") {
            id = "convention.android"
            implementationClass = "KotlinAndroidPlugin"
        }
    }
}
