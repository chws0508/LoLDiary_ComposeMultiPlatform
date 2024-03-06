import extensions.configureIos

plugins { id("convention.feature") }

android { namespace = "com.woosuk.onboarding" }

kotlin { configureIos(baseName = "domain") }
