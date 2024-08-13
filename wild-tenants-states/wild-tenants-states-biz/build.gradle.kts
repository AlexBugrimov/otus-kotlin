plugins {
    id("build-jvm")
}

kotlin {
    sourceSets {
        all { languageSettings.optIn("kotlin.RequiresOptIn") }
    }
}

dependencies {
    implementation(kotlin("stdlib-common"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.wt.cor)
    implementation(libs.coroutines.core)
    implementation(projects.wildTenantsStatesCommon)
    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
    testImplementation(kotlin("test-junit"))
    testApi(libs.coroutines.test)
}