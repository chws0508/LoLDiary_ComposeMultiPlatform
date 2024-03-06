package extensions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureApplicationPlugin() {
    with(pluginManager) {
        apply("com.android.application")
    }

    extensions.configure<ApplicationExtension> {
        configureAndroidCompose(this)
    }
}
class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureApplicationPlugin()
        }
    }
}
