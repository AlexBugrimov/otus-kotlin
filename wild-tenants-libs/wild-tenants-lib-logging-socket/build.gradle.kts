plugins {
    id("build-jvm")
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    implementation(kotlin("stdlib-common"))
    implementation(projects.wildTenantsLibLoggingCommon)
    implementation(libs.coroutines.core)
    implementation(libs.ktor.network)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.atomicfu)
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
    testImplementation(libs.coroutines.test)
    testImplementation(kotlin("test-junit"))
}
