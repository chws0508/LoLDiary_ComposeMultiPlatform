import extensions.configureIos

plugins { id("convention.feature") }

android { namespace = "com.woosuk.home" }

kotlin {
   configureIos("home")
}
