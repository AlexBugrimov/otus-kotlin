package ru.bugrimov.wt.biz.validation

import ru.bugrimov.wt.biz.WtProcessor
import ru.bugrimov.wt.common.WtCorSettings
import ru.bugrimov.wt.common.models.WtCommand

abstract class BaseBizValidationTest {

    protected abstract val command: WtCommand
    private val settings by lazy { WtCorSettings() }
    protected val processor by lazy { WtProcessor(settings) }
}
