plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("build-jvm") {
            id = "build-jvm"
            implementationClass = "ru.bugrimov.plugins.BuildPluginJvm"
        }
        register("build-kmp") {
            id = "build-kmp"
            implementationClass = "ru.bugrimov.plugins.BuildPluginMultiplatform"
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // enable Ktlint formatting
//    add("detektPlugins", libs.plugin.detektFormatting)

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(libs.plugin.kotlin)
//    implementation(libs.plugin.dokka)
    implementation(libs.plugin.binaryCompatibilityValidator)
//    implementation(libs.plugin.mavenPublish)
}
