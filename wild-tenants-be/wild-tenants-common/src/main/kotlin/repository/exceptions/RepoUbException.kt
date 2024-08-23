package ru.bugrimov.wt.common.repository.exceptions

import ru.bugrimov.wt.common.models.WtUbId

open class RepoUbException(
    @Suppress("unused")
    val adId: WtUbId,
    msg: String,
): RepoException(msg)
