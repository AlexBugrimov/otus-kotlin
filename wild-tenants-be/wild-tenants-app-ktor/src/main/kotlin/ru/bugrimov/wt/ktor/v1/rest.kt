package ru.bugrimov.wt.ktor.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.bugrimov.wt.ktor.WtAppSettings

fun Route.v1Ub(appSettings: WtAppSettings) {
    route("ub") {
        post("create") {
            call.createUb(appSettings)
        }
        post("read") {
            call.readUb(appSettings)
        }
        post("update") {
            call.updateUb(appSettings)
        }
        post("delete") {
            call.deleteUb(appSettings)
        }
        post("search") {
            call.searchUb(appSettings)
        }
    }
}
