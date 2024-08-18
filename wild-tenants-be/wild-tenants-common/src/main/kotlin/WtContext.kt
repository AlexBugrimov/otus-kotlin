package ru.bugrimov.wt.common

import kotlinx.datetime.Instant
import ru.bugrimov.wt.common.models.*
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

    var ubValidating: WtUb = WtUb(),
    var ubFilterValidating: WtUbFilter = WtUbFilter(),
    var ubValidated: WtUb = WtUb(),
    var ubFilterValidated: WtUbFilter = WtUbFilter(),

    val errors: MutableList<WtError> = mutableListOf(),
)