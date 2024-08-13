plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib-common"))
    implementation(libs.kotlinx.datetime)
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
    testImplementation(kotlin("test-junit"))
}
