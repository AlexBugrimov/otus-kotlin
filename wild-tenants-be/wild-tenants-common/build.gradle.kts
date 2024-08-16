plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib-common"))
    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
    api(libs.kotlinx.datetime)
    implementation(libs.coroutines.core)
    api(libs.wt.state.common)
}

