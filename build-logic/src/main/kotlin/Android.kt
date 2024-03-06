import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureAndroidPlugin() {
    with(pluginManager) {
        apply("com.android.library")
    }

    extensions.configure<LibraryExtension> {
        configureAndroidCompose(this)
    }
}
class AndroidPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureAndroidPlugin()
        }
    }
}
