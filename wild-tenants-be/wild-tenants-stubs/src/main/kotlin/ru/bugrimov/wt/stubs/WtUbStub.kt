package ru.bugrimov.wt.stubs

import ru.bugrimov.wt.common.models.UbPeriod
import ru.bugrimov.wt.common.models.WtUb
import ru.bugrimov.wt.common.models.WtUbId
import ru.bugrimov.wt.stubs.WtUbStubs.WT_UB

object WtUbStub {
    fun get(): WtUb = WT_UB.copy()

    fun prepareResult(block: WtUb.() -> Unit): WtUb = get().apply(block)

    fun prepareSearchList(period: UbPeriod = UbPeriod.CURRENT): List<WtUb> = listOf(
        WtUb(WtUbId("1"), ubPeriod = period),
        WtUb(WtUbId("2"), ubPeriod = period),
        WtUb(WtUbId("3"), ubPeriod = period),
        WtUb(WtUbId("4"), ubPeriod = period),
        WtUb(WtUbId("5"), ubPeriod = period),
    )
}
