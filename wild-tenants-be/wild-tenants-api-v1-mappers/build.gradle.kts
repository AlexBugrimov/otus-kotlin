plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(projects.wildTenantsCommon)
    implementation(projects.wildTenantsApiV1Jackson)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}