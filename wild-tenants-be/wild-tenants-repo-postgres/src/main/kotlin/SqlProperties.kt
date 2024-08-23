package ru.bugrimov.wt.backend.repo.postgresql

data class SqlProperties(
    val host: String = "localhost",
    val port: Int = 5432,
    val user: String = "postgres",
    val password: String = "wt_pass",
    val database: String = "wt_db",
    val schema: String = "public",
    val utilityBillTable: String = "utility_bill",
    val meterReadingTable: String = "meter_reading",
) {
    val url: String
        get() = "jdbc:postgresql://${host}:${port}/${database}"
}