package ru.bugrimov.wt.common.repository

import ru.bugrimov.wt.common.models.WtError
import ru.bugrimov.wt.common.models.WtUb

sealed interface IDbUbsResponse : IDbResponse<List<WtUb>>

data class DbUbsResponseOk(
    val data: List<WtUb>
) : IDbUbsResponse

@Suppress("unused")
data class DbUbsResponseErr(
    val errors: List<WtError> = emptyList()
) : IDbUbsResponse {
    constructor(err: WtError) : this(listOf(err))
}
