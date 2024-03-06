import extensions.compose
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
        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64(),
        ).forEach { target ->
            target.binaries.framework {
                baseName = "ComposeApp"
                isStatic = true
            }
        }
        configureSourceSets {
            all {
                languageSettings {
                    optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
                }
            }
            commonMain.dependencies {
                implementation(compose(this@configureComposeMultiplatform).runtime)
                implementation(compose(this@configureComposeMultiplatform).material3)
                implementation(compose(this@configureComposeMultiplatform).materialIconsExtended)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose(this@configureComposeMultiplatform).components.resources)
                implementation(project(":core:domain"))

                implementation(libs.findLibrary("voyager.navigator"))
                implementation(libs.findLibrary("composeImageLoader"))
                implementation(libs.findLibrary("kotlinx.coroutines.core"))
                implementation(libs.findLibrary("moko.mvvm"))
                implementation(libs.findLibrary("ktor.core"))
                implementation(libs.findLibrary("kotlinx.serialization.json"))
                implementation(libs.findLibrary("koin.core"))
            }

            androidMain.dependencies {
                implementation(libs.findLibrary("androidx.appcompat"))
                implementation(libs.findLibrary("compose.uitooling"))
                implementation(libs.findLibrary("kotlinx.coroutines.android"))
            }

            iosMain.dependencies {
            }
        }
    }
}

class KotlinMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureComposeMultiplatform()
        }
    }
}
