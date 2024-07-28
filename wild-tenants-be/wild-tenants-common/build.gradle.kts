plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib-common"))
    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
    api(libs.kotlinx.datetime)
    api("ru.bugrimov.wild_tenants.libs:wild-tenants-lib-logging-common")
}

