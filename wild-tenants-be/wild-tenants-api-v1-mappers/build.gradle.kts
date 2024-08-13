plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(projects.wildTenantsCommon)
    implementation(projects.wildTenantsApiV1Jackson)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}