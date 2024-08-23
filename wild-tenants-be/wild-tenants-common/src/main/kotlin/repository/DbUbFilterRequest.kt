package ru.bugrimov.wt.common.repository

import ru.bugrimov.wt.common.models.UbPeriod
import ru.bugrimov.wt.common.models.WtUserId

data class DbUbFilterRequest(
    val periodFilter: UbPeriod = UbPeriod.CURRENT,
    val ownerId: WtUserId = WtUserId.NONE
)
