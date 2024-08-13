plugins {
    id("build-jvm")
}

dependencies {
    implementation(projects.wildTenantsCommon)
    api(projects.wildTenantsRepoCommon)
    implementation(libs.coroutines.core)
    implementation(libs.db.cache4k)
    implementation(libs.uuid)
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
    testImplementation(projects.wildTenantsRepoTests)
    testImplementation(kotlin("test-junit"))
}