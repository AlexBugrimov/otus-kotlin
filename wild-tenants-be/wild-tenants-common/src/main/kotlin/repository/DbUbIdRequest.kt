package ru.bugrimov.wt.common.repository

import ru.bugrimov.wt.common.models.WtUb
import ru.bugrimov.wt.common.models.WtUbId
import ru.bugrimov.wt.common.models.WtUbLock

data class DbUbIdRequest(
    val id: WtUbId,
    val lock: WtUbLock = WtUbLock.NONE,
) {
    constructor(ad: WtUb) : this(ad.id, ad.lock)
}