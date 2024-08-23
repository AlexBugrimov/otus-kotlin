package ru.otus.otuskotlin.marketplace.repo.common

import ru.bugrimov.wt.common.models.WtUb

class UbRepoInitialized(
    val repo: IRepoUbInitialize,
    initObjects: Collection<WtUb> = emptyList(),
) : IRepoUbInitialize by repo {
    @Suppress("unused")
    val initializedObjects: List<WtUb> = save(initObjects).toList()
}
