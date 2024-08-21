package ru.bugrimov.wt.common

import kotlinx.datetime.Instant
import ru.bugrimov.wt.common.models.*
import ru.bugrimov.wt.common.repository.IRepoUb
import ru.bugrimov.wt.common.stubs.WtStubs
import ru.bugrimov.wt.common.ws.IWsSession

data class WtContext(
    var command: WtCommand = WtCommand.NONE,
    var state: WtState = WtState.NONE,
    var workMode: WtWorkMode = WtWorkMode.PROD,
    var stubCase: WtStubs = WtStubs.NONE,
    var wsSession: IWsSession = IWsSession.NONE,
    var requestId: WtRequestId = WtRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var request: WtUb = WtUb.NONE,
    var filterRequest: WtUbFilter = WtUbFilter(),
    var response: WtUb = WtUb.NONE,
    var corSettings: WtCorSettings = WtCorSettings(),
    var ubsResponse: MutableList<WtUb> = mutableListOf(),

    var ubValidating: WtUb = WtUb.NONE,
    var ubFilterValidating: WtUbFilter = WtUbFilter(),
    var ubValidated: WtUb = WtUb.NONE,
    var ubFilterValidated: WtUbFilter = WtUbFilter(),

    var ubRepo: IRepoUb = IRepoUb.NONE,
    var ubRepoRead: WtUb = WtUb.NONE,
    var ubRepoPrepare: WtUb = WtUb.NONE,
    var ubRepoDone: WtUb = WtUb.NONE,
    var ubsRepoDone: MutableList<WtUb> = mutableListOf(),

    val errors: MutableList<WtError> = mutableListOf(),
)