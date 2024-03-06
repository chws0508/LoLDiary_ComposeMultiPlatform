import extensions.configureSourceSets
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureKotlinMultiPlatform() {
    with(pluginManager) {
        apply("org.jetbrains.kotlin.multiplatform")
    }

    extensions.configure<KotlinMultiplatformExtension> {
        jvm()

        configureSourceSets {
            commonMain.dependencies {
            }
            commonTest.dependencies {
                implementation(libs.findLibrary("kotlin.test").get())
            }
        }
    }
}

class KotlinPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureKotlinMultiPlatform()
        }
    }
}
