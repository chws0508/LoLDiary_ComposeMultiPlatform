package extensions

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet


internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.android(action: CommonExtension<*, *, *, *, *>.() -> Unit) {
    val androidExtension = extensions.getByName("android")
    if (androidExtension is CommonExtension<*, *, *, *, *>) {
        androidExtension.apply(action)
    }
}

internal fun KotlinMultiplatformExtension.configureSourceSets(
    action: Action<NamedDomainObjectContainer<KotlinSourceSet>>,
) {
    sourceSets {
        action(this)
    }
}

internal fun KotlinMultiplatformExtension.compose(plugin: Project):
        ComposePlugin.Dependencies = ComposePlugin.Dependencies(plugin)
