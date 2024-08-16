plugins {
    id("build-jvm")
}

dependencies {
    implementation(projects.wildTenantsCommon)
    implementation(projects.wildTenantsStubs)
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.coroutines.core)
    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
    testImplementation(projects.wildTenantsRepoTests)
    testImplementation(kotlin("test-junit"))
}
