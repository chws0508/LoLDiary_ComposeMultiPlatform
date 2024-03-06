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

internal fun KotlinMultiplatformExtension.configureSourceSets(
    action: Action<NamedDomainObjectContainer<KotlinSourceSet>>,
) {
    sourceSets {
        action(this)
    }
}

internal fun Project.kotlin(action: CommonExtension<*, *, *, *, *>.() -> Unit) {
    val kotlinExtension = extensions.getByName("kotlin")
    if (kotlinExtension is CommonExtension<*, *, *, *, *>) {
        kotlinExtension.apply(action)
    }
}

internal val Project.compose: ComposePlugin.Dependencies
    get() = ComposePlugin.Dependencies(this)

fun KotlinMultiplatformExtension.configureIos(
    baseName: String
) = listOf(
    iosX64(),
    iosArm64(),
    iosSimulatorArm64(),
).forEach { iosTarget ->
    iosTarget.binaries.framework {
        this.baseName = baseName
        isStatic = true
    }
}
