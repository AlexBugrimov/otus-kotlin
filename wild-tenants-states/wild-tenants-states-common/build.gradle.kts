plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib-common"))
    implementation(libs.coroutines.core)
    api(libs.kotlinx.datetime)
    api(libs.wt.logs.common)
    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
}
