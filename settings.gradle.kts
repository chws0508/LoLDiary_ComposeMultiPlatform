rootProject.name = "LoLDiary"
include(":composeApp")
include(":core:domain")
include(":core:network")
include(":core:database")
include(":core:data")
include(":core:designsystem")
include(":core:ui")
include(":core:navigation")
include(":feature:onboarding")
include(":feature:home")
include(":feature:calendar")
include(":feature:matchdetails")
include(":feature:setting")

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
