plugins {
    id("build-jvm")
}

dependencies {
    api(kotlin("test-common"))
    api(kotlin("test-annotations-common"))
    api(libs.coroutines.core)
    api(libs.coroutines.test)
    implementation(projects.wildTenantsCommon)
    implementation(projects.wildTenantsRepoCommon)
    testImplementation(projects.wildTenantsStubs)
    api(kotlin("test-junit"))
}