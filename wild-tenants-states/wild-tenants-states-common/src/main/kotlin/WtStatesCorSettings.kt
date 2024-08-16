package ru.bugrimov.wt.states.common

import ru.bugrimov.wt.logging.common.LoggerProvider

data class WtStatesCorSettings(
    val loggerProvider: LoggerProvider = LoggerProvider()
) {
    companion object {
        val NONE = WtStatesCorSettings()
    }
}
