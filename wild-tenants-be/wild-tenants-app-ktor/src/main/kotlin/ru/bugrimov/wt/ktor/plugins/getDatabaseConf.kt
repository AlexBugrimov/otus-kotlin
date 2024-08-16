package ru.bugrimov.wt.ktor.plugins

import ru.bugrimov.wt.repo.inmemory.UbRepoInMemory
import io.ktor.server.application.*
import ru.bugrimov.wt.common.repository.IRepoWt
import ru.bugrimov.wt.ktor.configurations.ConfigPaths.REPOSITORY
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

fun Application.getDatabaseConf(type: UbDbType): IRepoWt {
    val dbSettingPath = "$REPOSITORY.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    return when (dbSetting) {
        "in-memory", "memory", "mem" -> initInMemory()
        else -> throw IllegalArgumentException(
            "$dbSettingPath must be set in application.yml to one of: " +
                    "'in-memory', 'postgres', 'cassandra', 'gremlin'"
        )
    }
}

enum class UbDbType(val confName: String) {
    PROD("prod"), TEST("test")
}

fun Application.initInMemory(): IRepoWt {
    val ttlSetting = environment.config.propertyOrNull("db.prod")?.getString()?.let {
        Duration.parse(it)
    }
    return UbRepoInMemory(ttl = ttlSetting ?: 10.minutes)
}
