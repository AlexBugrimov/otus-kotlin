package ru.bugrimov.wt.common

import ru.bugrimov.wt.common.repository.IRepoUb
import ru.bugrimov.wt.common.ws.IWsSessionRepo
import ru.bugrimov.wt.logging.common.LoggerProvider
import ru.bugrimov.wt.states.common.WtStatesCorSettings

data class WtCorSettings(
    val loggerProvider: LoggerProvider = LoggerProvider(),
    val stateSettings: WtStatesCorSettings = WtStatesCorSettings(),
    val wsSessions: IWsSessionRepo = IWsSessionRepo.NONE,
    val stubRepository: IRepoUb = IRepoUb.NONE,
    val testRepository: IRepoUb = IRepoUb.NONE,
    val prodRepository: IRepoUb = IRepoUb.NONE,
) {

    companion object {
        val NONE = WtCorSettings()
    }
}