import extensions.configureIos

plugins {
    id("convention.kotlin")
}

kotlin {
    configureIos(baseName = "domain")
    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.paging.common) // without android dependencies paging
            implementation(libs.napier)
        }
    }
}
