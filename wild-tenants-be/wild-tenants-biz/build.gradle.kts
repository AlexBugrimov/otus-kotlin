plugins {
    alias(libs.plugins.kotlinx.serialization)
    id("build-jvm")
}

dependencies {
    implementation(projects.wildTenantsCommon)
    implementation(libs.wt.cor)
    implementation(projects.wildTenantsStubs)
    testImplementation(kotlin("test-junit"))
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
}