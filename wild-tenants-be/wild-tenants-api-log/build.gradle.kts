import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    id("build-jvm")
    alias(libs.plugins.openapi.generator)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    sourceSets {
        all { languageSettings.optIn("kotlin.RequiresOptIn") }
    }
}

dependencies {
    implementation(kotlin("stdlib-common"))
    implementation(libs.coroutines.core)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(projects.wildTenantsCommon)
    implementation(libs.jackson.kotlin)
    implementation(libs.jackson.datatype)
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
    testImplementation(kotlin("test-junit"))
}

openApiGenerate {
    val openapiGroup = "${rootProject.group}.api.log"
    generatorName.set("kotlin")
    packageName.set(openapiGroup)
    apiPackage.set("$openapiGroup.api")
    modelPackage.set("$openapiGroup.models")
    invokerPackage.set("$openapiGroup.invoker")
    inputSpec.set(rootProject.ext["spec-log"] as String)

    globalProperties.apply {
        put("models", "")
        put("modelDocs", "false")
    }

    configOptions.set(
        mapOf(
            "dateLibrary" to "string",
            "enumPropertyNaming" to "UPPERCASE",
            "serializationLibrary" to "jackson",
            "collectionType" to "list"
        )
    )
}

tasks {
    compileKotlin {
        dependsOn(openApiGenerate)
    }
}

sourceSets {
    main {
        kotlin.srcDir(layout.buildDirectory.dir("generate-resources/main/src/main/kotlin"))
    }
}