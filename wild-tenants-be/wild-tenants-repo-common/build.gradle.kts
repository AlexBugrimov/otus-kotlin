plugins {
    id("build-jvm")
}

dependencies {
    implementation(projects.wildTenantsCommon)
    implementation(libs.coroutines.core)
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
    testImplementation(kotlin("test-junit"))
}