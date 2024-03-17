import extensions.compose
import extensions.configureAndroidPlugin
import extensions.configureSourceSets
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureKotlinAndroidPlugin() {
    with(pluginManager) {
        apply("org.jetbrains.kotlin.multiplatform")
        apply("org.jetbrains.compose")
    }
    configureAndroidPlugin()
    extensions.configure<KotlinMultiplatformExtension> {
        applyDefaultHierarchyTemplate()
        androidTarget {
            compilations.all {
                kotlinOptions {
                    jvmTarget = "17"
                }
            }
        }

        configureSourceSets {
            commonMain.dependencies {
                implementation(compose.runtime)
                implementation(libs.findLibrary("koin.compose").get())
                implementation(libs.findLibrary("koin.core").get())
                implementation(libs.findLibrary("kotlinx.coroutines.core").get())
                implementation(libs.findLibrary("touchlab.common").get())
            }
            commonTest.dependencies {
                implementation(libs.findLibrary("kotlin.test").get())
            }
            androidMain {
                dependsOn(commonMain.get())
            }
            iosMain {
                dependsOn(commonMain.get())
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
