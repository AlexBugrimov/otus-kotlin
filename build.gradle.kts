plugins {
    kotlin("jvm") apply false
}

group = "ru.bugrimov"
version = "1.0"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}