package ru.bugrimov.otuskotlin.marketplace.api.v1

import ru.bugrimov.wild_tenants.be.api.v1.models.*
import ru.bugrimov.wt.api.v1.apiV1Mapper
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestV1SerializationTest {

    private val request = CreateRequest(
        requestType = "create",
        debug = UbDebug(
            mode = RequestDebugMode.STUB,
            stub = RequestDebugStubs.BAD_TITLE
        ),
        ub = CreateObject(
            meterReadings = listOf(
                MeterReading(
                    name = MeterReading.Name.ELECTRICITY,
                    value = BigDecimal(7300.0),
                    volumeForPeriod = BigDecimal(123),
                    accruedSum = BigDecimal(845.50),
                    paidAmount = BigDecimal(800.0),
                )
            ),
            period = Period(Period.Month.JAN, 2004)
        )
    )

    @Test
    fun `should serialize the request`() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"name\":\\s*\"electricity\""))
        assertContains(json, Regex("\"value\":\\s*7300"))
        assertContains(json, Regex("\"volumeForPeriod\":\\s*123"))
        assertContains(json, Regex("\"accruedSum\":\\s*845.5"))
        assertContains(json, Regex("\"paidAmount\":\\s*800"))
    }

    @Test
    fun `should deserialize the request`() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as CreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun `should deserialize naked`() {
        val jsonString = """
            {"ub": null}
        """.trimIndent()
        val obj = apiV1Mapper.readValue(jsonString, CreateRequest::class.java)

        assertEquals(null, obj.ub)
    }
}
