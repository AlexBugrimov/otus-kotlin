dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
pluginManagement {
    includeBuild("../plugins")
    plugins {
        id("build-jvm") apply false
        id("build-kmp") apply false
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "wild-tenants-be"

include(
    "app",
    "wild-tenants-api-v1-jackson",
    "wild-tenants-api-v1-mappers",
    "wild-tenants-common"
)
include()

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
