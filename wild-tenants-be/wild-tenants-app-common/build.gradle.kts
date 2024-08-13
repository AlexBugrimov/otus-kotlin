plugins {
    alias(libs.plugins.kotlinx.serialization)
    id("build-jvm")
}

dependencies {
    implementation(projects.wildTenantsBiz)
    implementation(projects.wildTenantsCommon)
    implementation(projects.wildTenantsApiLog)
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.coroutines.core)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
    testImplementation(kotlin("test"))
    testImplementation(libs.coroutines.test)
}