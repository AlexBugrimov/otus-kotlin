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

    ":wild-tenants-api-v1-jackson",
    ":wild-tenants-api-log",

    ":wild-tenants-app-ktor",
    ":wild-tenants-app-common",
    ":wild-tenants-api-v1-mappers",

    ":wild-tenants-biz",
    ":wild-tenants-common",
    ":wild-tenants-stubs",

    ":wild-tenants-repo-common",
    ":wild-tenants-repo-in-memory",
    ":wild-tenants-repo-stubs",
    ":wild-tenants-repo-tests"
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
