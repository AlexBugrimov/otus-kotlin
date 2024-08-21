package ru.otus.otuskotlin.marketplace.repo.common

import ru.bugrimov.wt.common.models.WtUb
import ru.bugrimov.wt.common.repository.IRepoUb

interface IRepoUbInitialize : IRepoUb {
    fun save(ubs: Collection<WtUb>): Collection<WtUb>
}
