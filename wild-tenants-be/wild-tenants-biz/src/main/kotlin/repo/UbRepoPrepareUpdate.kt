package ru.bugrimov.wt.biz.repo

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.models.WtState
import ru.bugrimov.wt.lib.cor.ICorChainDsl
import ru.bugrimov.wt.lib.cor.worker


fun ICorChainDsl<WtContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = """Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД,
            и данные, полученные от пользователя"""
    on { state == WtState.RUNNING }
    handle {
        ubRepoPrepare = ubRepoRead.deepCopy().apply {
            ubMeterReadings = ubValidated.ubMeterReadings
            ubPeriod = ubValidated.ubPeriod
            lock = ubValidated.lock
        }
    }
}
