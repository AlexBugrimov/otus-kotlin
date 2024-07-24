package ru.bugrimov.plugins

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

@Suppress("unused")
internal class BuildPluginJvm : Plugin<Project> {

    override fun apply(project: Project): Unit = with(project) {
        pluginManager.apply("org.jetbrains.kotlin.jvm")

        val libs = project.the<LibrariesForLibs>()

        dependencies.add("testImplementation", libs.bundles.kotest)
        group = rootProject.group
        version = rootProject.version

    }
}