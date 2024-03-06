rootProject.name = "LoLDiary"
include(":composeApp")
include(":core:domain")
include(":feature:onboarding")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
