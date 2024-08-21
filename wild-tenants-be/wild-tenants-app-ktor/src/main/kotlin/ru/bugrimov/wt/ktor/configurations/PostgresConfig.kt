package ru.bugrimov.wt.ktor.configurations

import io.ktor.server.config.*

data class PostgresConfig(
    val host: String = "localhost",
    val port: Int = 5432,
    val user: String = "postgres",
    val password: String = "wt_pass",
    val database: String = "wt_db",
    val schema: String = "public",
) {
    constructor(config: ApplicationConfig): this(
        host = config.propertyOrNull("$PATH.host")?.getString() ?: "localhost",
        port = config.propertyOrNull("$PATH.port")?.getString()?.toIntOrNull() ?: 5432,
        user = config.propertyOrNull("$PATH.user")?.getString() ?: "postgres",
        password = config.property("$PATH.password").getString(),
        database = config.propertyOrNull("$PATH.database")?.getString() ?: "wt_db",
        schema = config.propertyOrNull("$PATH.schema")?.getString() ?: "public",
    )

    companion object {
        const val PATH = "${ConfigPaths.REPOSITORY}.psql"
    }
}
