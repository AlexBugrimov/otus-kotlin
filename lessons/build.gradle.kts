plugins {
    alias(libs.plugins.kotlin.jvm) apply false
}

group = "ru.bugrimov.lessons"
version = "0.0.1"

subprojects {
    repositories {
        mavenCentral()
    }
    group = rootProject.group
    version = rootProject.version
}
