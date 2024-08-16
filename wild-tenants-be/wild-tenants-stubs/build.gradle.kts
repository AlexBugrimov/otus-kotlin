plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib-common"))
    implementation(projects.wildTenantsCommon)
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-junit"))
    testImplementation(kotlin("test-annotations-common"))
}