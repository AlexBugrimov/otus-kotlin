package ru.bugrimov.wt.ktor.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.bugrimov.wild_tenants.be.api.v1.models.IRequest
import ru.bugrimov.wild_tenants.be.api.v1.models.IResponse
import ru.bugrimov.wt.app.common.controllerHelper
import ru.bugrimov.wt.ktor.WtAppSettings
import ru.bugrimov.wt.mappers.fromTransport
import ru.bugrimov.wt.mappers.toTransportUb
import kotlin.reflect.KClass

suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IResponse> ApplicationCall.processV1(
    appSettings: WtAppSettings,
    target: KClass<*>,
    logId: String,
) = appSettings.controllerHelper(
    { fromTransport(receive<Q>()) },
    { respond(toTransportUb() as R) },
    target,
    logId,
)
