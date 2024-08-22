plugins {
    alias(libs.plugins.kotlin.jvm) apply false
}

group = "ru.bugrimov.wild_tenants.tests"
version = "0.0.1"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}

tasks {
    arrayOf("build", "clean", "check").forEach {tsk ->
        create(tsk) {
            group = "build"
            dependsOn(subprojects.map {  it.getTasksByName(tsk,false)})
        }
    }
//    create("e2eTests") {
//        dependsOn(project(":wild_tenants-e2e-be").tasks.getByName("check"))
//    }
}
