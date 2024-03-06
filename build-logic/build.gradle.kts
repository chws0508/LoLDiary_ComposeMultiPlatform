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
        register("kotlinMultiplatformPlugin") {
            id = "convention.multiplatform"
            implementationClass = "KotlinMultiplatformConventionPlugin"
        }
        register("androidPlugin") {
            id = "convention.android.library"
            implementationClass = "AndroidPlugin"
        }
        register("applicationPlugin") {
            id = "convention.android.application"
            implementationClass = "ApplicationPlugin"
        }
    }
}
