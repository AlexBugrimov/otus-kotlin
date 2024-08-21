package ru.bugrimov.wt.common.repository

import ru.bugrimov.wt.common.helpers.errorSystem
import ru.bugrimov.wt.common.models.WtError
import ru.bugrimov.wt.common.models.WtUb
import ru.bugrimov.wt.common.models.WtUbId
import ru.bugrimov.wt.common.models.WtUbLock
import ru.bugrimov.wt.common.repository.exceptions.RepoConcurrencyException
import ru.bugrimov.wt.common.repository.exceptions.RepoException

const val ERROR_GROUP_REPO = "repo"

fun errorNotFound(id: WtUbId) = DbUbResponseErr(
    WtError(
        code = "$ERROR_GROUP_REPO-not-found",
        group = ERROR_GROUP_REPO,
        field = "id",
        message = "Object with ID: ${id.asString()} is not Found",
    )
)

val errorEmptyId = DbUbResponseErr(
    WtError(
        code = "$ERROR_GROUP_REPO-empty-id",
        group = ERROR_GROUP_REPO,
        field = "id",
        message = "Id must not be null or blank"
    )
)

fun errorRepoConcurrency(
    oldAd: WtUb,
    expectedLock: WtUbLock,
    exception: Exception = RepoConcurrencyException(
        id = oldAd.id,
        expectedLock = expectedLock,
        actualLock = oldAd.lock,
    ),
) = DbUbResponseErrWithData(
    ub = oldAd,
    err = WtError(
        code = "$ERROR_GROUP_REPO-concurrency",
        group = ERROR_GROUP_REPO,
        field = "lock",
        message = "The object with ID ${oldAd.id.asString()} has been changed concurrently by another user or process",
        exception = exception,
    )
)

fun errorEmptyLock(id: WtUbId) = DbUbResponseErr(
    WtError(
        code = "$ERROR_GROUP_REPO-lock-empty",
        group = ERROR_GROUP_REPO,
        field = "lock",
        message = "Lock for Ad ${id.asString()} is empty that is not admitted"
    )
)

fun errorDb(e: RepoException) = DbUbResponseErr(
    errorSystem(
        violationCode = "dbLockEmpty",
        e = e
    )
)
