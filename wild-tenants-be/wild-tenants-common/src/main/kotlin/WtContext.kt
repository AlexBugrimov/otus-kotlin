package ru.bugrimov.windtenants.common

import kotlinx.datetime.Instant
import ru.bugrimov.windtenants.common.models.WtUb
import ru.bugrimov.windtenants.common.models.WtCommand
import ru.bugrimov.windtenants.common.models.WtError
import ru.bugrimov.windtenants.common.models.WtState
import ru.bugrimov.windtenants.common.stubs.WtStubs
import ru.bugrimov.windtenants.common.models.WtUbFilter
import ru.bugrimov.windtenants.common.models.WtRequestId
import ru.bugrimov.windtenants.common.models.WtWorkMode

data class WtContext(
    var command: WtCommand = WtCommand.NONE,
    var state: WtState = WtState.NONE,
    var workMode: WtWorkMode = WtWorkMode.PROD,
    var stubCase: WtStubs = WtStubs.NONE,
    var requestId: WtRequestId = WtRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var request: WtUb = WtUb.NONE,
    var filterRequest: WtUbFilter = WtUbFilter(),
    var response: WtUb = WtUb.NONE,
    var ubsResponse: MutableList<WtUb> = mutableListOf(),
    val errors: MutableList<WtError> = mutableListOf(),
)