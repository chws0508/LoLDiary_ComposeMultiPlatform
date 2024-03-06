import extensions.configureIos

plugins {
    id("convention.kotlin")
}

kotlin { configureIos(baseName = "domain") }
