package ru.bugrimov.wt.stubs

import ru.bugrimov.wt.common.models.WtUb
import ru.bugrimov.wt.common.models.WtUbId
import ru.bugrimov.wt.stubs.WtUbStubs.WT_UB

object WtUbStub {
    fun get(): WtUb = WT_UB.copy()

    fun prepareResult(block: WtUb.() -> Unit): WtUb = get().apply(block)

    fun prepareSearchList(filter: String): List<WtUb> = listOf(
        WtUb(WtUbId("1")),
        WtUb(WtUbId("2")),
        WtUb(WtUbId("3")),
        WtUb(WtUbId("4")),
        WtUb(WtUbId("5")),
    )
}
