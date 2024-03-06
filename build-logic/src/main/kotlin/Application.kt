import com.android.build.api.dsl.ApplicationExtension
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            with(pluginManager) {
                apply("com.android.application")
            }

            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    targetSdk = libs.findLibrary("targetSdk").get().toString().toInt()
                }

                buildFeatures {
                    buildConfig = true
                }

                configureAndroidCompose(this)
            }
        }
    }
}
