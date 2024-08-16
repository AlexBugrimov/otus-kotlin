plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
}

group = "ru.bugrimov.wild_tenants.be"
version = "0.0.1"

allprojects {
    repositories {
        mavenCentral()
    }
}

ext {
    val specDir = layout.projectDirectory.dir("../specs")
    set("spec-v1", specDir.file("specs-ub-v1.yaml").toString())
    set("spec-log", specDir.file("specs-ub-log.yaml").toString())
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}
