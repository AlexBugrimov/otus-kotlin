package ru.bugrimov.wt.app.common

import ru.bugrimov.wt.common.WtCorSettings
import ru.bugrimov.wt.biz.WtProcessor

interface AppSettings {
    val processor: WtProcessor
    val corSettings: WtCorSettings
}