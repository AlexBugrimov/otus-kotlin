package ru.bugrimov.wt.common.repository.exceptions

import ru.bugrimov.wt.common.models.WtUbId
import ru.bugrimov.wt.common.models.WtUbLock

class RepoConcurrencyException(id: WtUbId, expectedLock: WtUbLock, actualLock: WtUbLock?) : RepoUbException(
    id,
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)
