
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
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "wild-tenants-libs"
include(
    ":wild-tenants-lib-cor",
            ":wild-tenants-lib-logging-common",
            ":wild-tenants-lib-logging-logback",
           ":wild-tenants-lib-logging-socket"
)
