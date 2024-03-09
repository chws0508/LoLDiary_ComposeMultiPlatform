import extensions.compose
import extensions.configureAndroidPlugin
import extensions.configureSourceSets
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureFeature() {
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
            all {
                languageSettings {
                    optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
                }
            }
            commonMain.dependencies {
                implementation(compose.runtime)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(project(":core:domain"))
                implementation(project(":core:designsystem"))

                implementation(libs.findLibrary("voyager.navigator").get())
                implementation(libs.findLibrary("voyager.screenModel").get())
                implementation(libs.findLibrary("voyager.koin").get())
                implementation(libs.findLibrary("composeImageLoader").get())
                implementation(libs.findLibrary("kotlinx.coroutines.core").get())
                implementation(libs.findLibrary("koin.core").get())
                implementation(libs.findLibrary("koin.compose").get())
                implementation(libs.findLibrary("touchlab.common").get())
            }

            commonTest.dependencies {
                implementation(libs.findLibrary("kotlin.test").get())
            }

            androidMain.dependencies {
                implementation(libs.findLibrary("koin.android").get())
                implementation(libs.findLibrary("androidx.appcompat").get())
                implementation(libs.findLibrary("compose.uitooling").get())
                implementation(libs.findLibrary("kotlinx.coroutines.android").get())
            }

            iosMain.dependencies {
            }
        }
    }
}

class FeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureFeature()
        }
    }
}
