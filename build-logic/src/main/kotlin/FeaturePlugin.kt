import extensions.compose
import extensions.configureAndroidPlugin
import extensions.configureSourceSets
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureComposeMultiplatform() {
    with(pluginManager) {
        apply("org.jetbrains.kotlin.multiplatform")
        apply("org.jetbrains.compose")
        apply("com.android.library")
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

        jvm()

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

                implementation(libs.findLibrary("voyager.navigator").get())
                implementation(libs.findLibrary("composeImageLoader").get())
                implementation(libs.findLibrary("kotlinx.coroutines.core").get())
                implementation(libs.findLibrary("moko.mvvm").get())
                implementation(libs.findLibrary("koin.core").get())
            }

            androidMain.dependencies {
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
            configureComposeMultiplatform()
        }
    }
}