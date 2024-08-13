package ru.bugrimov.wt.biz

import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.WtCorSettings
import ru.bugrimov.wt.common.models.WtState
import ru.bugrimov.wt.stubs.WtUbStub

@Suppress("unused", "RedundantSuspendModifier")
class WtProcessor(
    val corSettings: WtCorSettings
) {

    suspend fun exec(ctx: WtContext) {
        ctx.response = WtUbStub.get()
        ctx.ubsResponse = WtUbStub.prepareSearchList("ub search").toMutableList()
        ctx.state = WtState.RUNNING
    }
}