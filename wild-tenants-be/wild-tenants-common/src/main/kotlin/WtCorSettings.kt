package ru.bugrimov.wt.common

import ru.bugrimov.wt.common.repository.IRepoWt
import ru.bugrimov.wt.common.ws.IWsSessionRepo
import ru.bugrimov.wt.logging.common.LoggerProvider
import ru.bugrimov.wt.states.common.WtStatesCorSettings

data class WtCorSettings(
    val loggerProvider: LoggerProvider = LoggerProvider(),
    val stateSettings: WtStatesCorSettings = WtStatesCorSettings(),
    val wsSessions: IWsSessionRepo = IWsSessionRepo.NONE,
    val stubRepository: IRepoWt = IRepoWt.NONE,
    val testRepository: IRepoWt = IRepoWt.NONE,
    val prodRepository: IRepoWt = IRepoWt.NONE,
) {

    companion object {
        val NONE = WtCorSettings()
    }
}