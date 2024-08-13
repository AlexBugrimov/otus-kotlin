package ru.bugrimov.wt.common.repository

import ru.bugrimov.wt.common.models.WtError
import ru.bugrimov.wt.common.models.WtUb

sealed interface IDbUbResponse : IDbResponse<WtUb>

data class DbUbResponseOk(
    val data: WtUb
) : IDbUbResponse

data class DbUbResponseErr(
    val errors: List<WtError> = emptyList()
) : IDbUbResponse {
    constructor(err: WtError) : this(listOf(err))
}

data class DbUbResponseErrWithData(
    val data: WtUb,
    val errors: List<WtError> = emptyList()
) : IDbUbResponse {
    constructor(ub: WtUb, err: WtError) : this(ub, listOf(err))
}
