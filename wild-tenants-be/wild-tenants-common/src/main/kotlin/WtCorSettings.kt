package ru.bugrimov.wt.common

import ru.bugrimov.wt.common.repository.IRepoWt
import ru.bugrimov.wt.common.repository.IRepoWt.Companion.NONE
import ru.bugrimov.wt.common.ws.IWsSessionRepo
import ru.bugrimov.wt.logging.common.LoggerProvider
import ru.bugrimov.wt.states.common.WtStatesCorSettings

data class WtCorSettings(
    val loggerProvider: LoggerProvider = LoggerProvider(),
    val stateSettings: WtStatesCorSettings = WtStatesCorSettings(),
    val wsSessions: IWsSessionRepo = IWsSessionRepo.NONE,
    val stubRepository: IRepoWt = NONE,
    val testRepository: IRepoWt = NONE,
    val prodRepository: IRepoWt = NONE,
) {

    companion object {
        val NODE = WtCorSettings()
    }
}