package ru.bugrimov.wt.ktor.v1

import io.ktor.server.application.*
import ru.bugrimov.wild_tenants.be.api.v1.models.*
import ru.bugrimov.wt.app.common.AppSettings
import ru.bugrimov.wt.ktor.WtAppSettings
import kotlin.reflect.KClass

val clCreate: KClass<*> = ApplicationCall::createUb::class
suspend fun ApplicationCall.createUb(appSettings: WtAppSettings) =
    processV1<CreateRequest, CreateResponse>(appSettings, clCreate, "create")

val clRead: KClass<*> = ApplicationCall::createUb::class
suspend fun ApplicationCall.readUb(appSettings: WtAppSettings) =
    processV1<ReadRequest, ReadResponse>(appSettings, clRead, "read")

val clUpdate: KClass<*> = ApplicationCall::createUb::class
suspend fun ApplicationCall.updateUb(appSettings: WtAppSettings) =
    processV1<UpdateRequest, UpdateResponse>(appSettings, clUpdate, "update")

val clDelete: KClass<*> = ApplicationCall::createUb::class
suspend fun ApplicationCall.deleteUb(appSettings: WtAppSettings) =
    processV1<DeleteRequest, DeleteResponse>(appSettings, clDelete, "delete")

val clSearch: KClass<*> = ApplicationCall::createUb::class
suspend fun ApplicationCall.searchUb(appSettings: WtAppSettings) =
    processV1<SearchRequest, SearchResponse>(appSettings, clSearch, "search")
