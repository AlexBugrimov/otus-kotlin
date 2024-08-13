package ru.bugrimov.wt.ktor

import ru.bugrimov.wt.common.WtCorSettings
import ru.bugrimov.wt.app.common.AppSettings
import ru.bugrimov.wt.biz.WtProcessor

class WtAppSettings(
    val urls: List<String> = emptyList(),
    override val corSettings: WtCorSettings = WtCorSettings(),
    override val processor: WtProcessor = WtProcessor(corSettings)
) : AppSettings