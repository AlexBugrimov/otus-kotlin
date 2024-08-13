package ru.bugrimov.wt.ktor.plugins

import io.ktor.server.application.*
import ru.bugrimov.wt.backend.repository.inmemory.WtRepoStub
import ru.bugrimov.wt.biz.WtProcessor
import ru.bugrimov.wt.common.WtCorSettings
import ru.bugrimov.wt.ktor.WtAppSettings
import ru.bugrimov.wt.ktor.base.KtorWsSessionRepo
import ru.bugrimov.wt.states.common.WtStatesCorSettings

fun Application.settings(): WtAppSettings {
    val loggerProvider = getLoggerProviderConf()
    val corSettings = WtCorSettings(
        loggerProvider = loggerProvider,
        wsSessions = KtorWsSessionRepo(),
        testRepository = getDatabaseConf(UbDbType.TEST),
        prodRepository = getDatabaseConf(UbDbType.PROD),
        stubRepository = WtRepoStub(),
        stateSettings = WtStatesCorSettings(loggerProvider = loggerProvider),
    )
    return WtAppSettings(
        urls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = WtProcessor(corSettings),
    )
}