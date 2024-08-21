package ru.bugrimov.wt.ktor.plugins

import ru.bugrimov.wt.repo.inmemory.UbRepoInMemory
import io.ktor.server.application.*
import ru.bugrimov.wt.backend.repo.postgresql.RepoUbSql
import ru.bugrimov.wt.backend.repo.postgresql.SqlProperties
import ru.bugrimov.wt.common.repository.IRepoUb
import ru.bugrimov.wt.ktor.configurations.ConfigPaths.REPOSITORY
import ru.bugrimov.wt.ktor.configurations.PostgresConfig
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

fun Application.getDatabaseConf(type: UbDbType): IRepoUb {
    val dbSettingPath = "$REPOSITORY.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    return when (dbSetting) {
        "in-memory", "memory", "mem" -> initInMemory()
        "postgres", "postgresql", "pg", "sql", "psql" -> initPostgres()
        else -> throw IllegalArgumentException(
            "$dbSettingPath must be set in application.yml to one of: " +
                    "'in-memory', 'postgres', 'cassandra', 'gremlin'"
        )
    }
}

enum class UbDbType(val confName: String) {
    PROD("prod"), TEST("test")
}

fun Application.initInMemory(): IRepoUb {
    val ttlSetting = environment.config.propertyOrNull("db.prod")?.getString()?.let {
        Duration.parse(it)
    }
    return UbRepoInMemory(ttl = ttlSetting ?: 10.minutes)
}

fun Application.initPostgres(): IRepoUb {
    val config = PostgresConfig(environment.config)
    return RepoUbSql(
        properties = SqlProperties(
            host = config.host,
            port = config.port,
            user = config.user,
            password = config.password,
            schema = config.schema,
            database = config.database,
        )
    )
}
