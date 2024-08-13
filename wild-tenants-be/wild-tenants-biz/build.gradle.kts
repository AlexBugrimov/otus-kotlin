plugins {
    alias(libs.plugins.kotlinx.serialization)
    id("build-jvm")
}

dependencies {
    implementation(projects.wildTenantsCommon)
    implementation(libs.wt.cor)
    implementation(projects.wildTenantsStubs)
}