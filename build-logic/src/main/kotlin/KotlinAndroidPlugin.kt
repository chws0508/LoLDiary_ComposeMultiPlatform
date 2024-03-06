import extensions.configureAndroidPlugin
import extensions.configureSourceSets
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.exclude
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureKotlinAndroidPlugin() {
    with(pluginManager) {
        apply("org.jetbrains.kotlin.multiplatform")
    }
    configureAndroidPlugin()
    extensions.configure<KotlinMultiplatformExtension> {

        androidTarget {
            compilations.all {
                kotlinOptions {
                    jvmTarget = "17"
                }
            }
        }

        jvm()

        configureSourceSets {
            commonMain.dependencies {
                implementation(libs.findLibrary("koin.core").get())
                implementation(libs.findLibrary("kotlinx.coroutines.core").get())
                implementation(libs.findLibrary("touchlab.common").get())
            }
            commonTest.dependencies {
                implementation(libs.findLibrary("kotlin.test").get())
            }
        }
    }
}

class KotlinAndroidPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureKotlinAndroidPlugin()
        }
    }
}

