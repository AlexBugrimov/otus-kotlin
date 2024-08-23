package ru.bugrimov.wt.common

import ru.bugrimov.wt.common.repository.IRepoUb
import ru.bugrimov.wt.common.ws.IWsSessionRepo
import ru.bugrimov.wt.logging.common.LoggerProvider
import ru.bugrimov.wt.states.common.WtStatesCorSettings

data class WtCorSettings(
    val loggerProvider: LoggerProvider = LoggerProvider(),
    val stateSettings: WtStatesCorSettings = WtStatesCorSettings(),
    val wsSessions: IWsSessionRepo = IWsSessionRepo.NONE,
    val repoStub: IRepoUb = IRepoUb.NONE,
    val repoTest: IRepoUb = IRepoUb.NONE,
    val repoProd: IRepoUb = IRepoUb.NONE,
) {

    companion object {
        val NONE = WtCorSettings()
    }
}