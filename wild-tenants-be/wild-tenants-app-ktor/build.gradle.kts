import io.ktor.plugin.features.*

plugins {
    alias(libs.plugins.kotlinx.serialization)
    id("build-jvm")
    alias(libs.plugins.ktor)
    alias(libs.plugins.muschko.remote)
}

application {
    mainClass.set("io.ktor.server.cio.EngineMain")
}

ktor {
    configureNativeImage(project)
    docker {
        localImageName.set(project.name)
        imageTag.set(project.version.toString())
        jreVersion.set(JavaVersion.VERSION_21)
    }
}

jib {
    container.mainClass = application.mainClass.get()
}

dependencies {
    implementation(projects.wildTenantsAppCommon)
    implementation(projects.wildTenantsCommon)
    implementation(projects.wildTenantsBiz)
    implementation(projects.wildTenantsRepoInMemory)
    implementation(projects.wildTenantsApiV1Jackson)
    implementation(projects.wildTenantsApiV1Mappers)
    implementation(projects.wildTenantsRepoStubs)
    implementation(projects.wildTenantsRepoPostgres)
    implementation(libs.ktor.client.negotiation)
    implementation(kotlin("stdlib-common"))
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cio)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.yaml)
    implementation(libs.ktor.server.negotiation)
    implementation(libs.ktor.server.headers.response)
    implementation(libs.ktor.server.headers.caching)
    implementation(libs.ktor.server.websocket)
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.ktor.serialization.jackson)
    implementation(libs.ktor.server.calllogging)
    implementation(libs.ktor.server.headers.default)
    implementation(libs.wt.logs.common)
    implementation(libs.wt.logs.logback)
    implementation(libs.wt.logs.socket)
    implementation(libs.wt.state.common)
    implementation(libs.wt.state.biz)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.serialization.json)
    testImplementation(kotlin("test-junit"))
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
    testImplementation(libs.ktor.server.test)
}