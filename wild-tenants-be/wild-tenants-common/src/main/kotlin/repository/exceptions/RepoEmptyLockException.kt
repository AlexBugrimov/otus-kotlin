package ru.bugrimov.wt.common.repository.exceptions

import ru.bugrimov.wt.common.models.WtUbId

class RepoEmptyLockException(id: WtUbId) : RepoUbException(
    id,
    "Lock is empty in DB"
)
