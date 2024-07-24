package ru.otus.otuskotlin.marketplace.api.v1

import ru.bugrimov.wild_tenants.be.api.v1.models.*
import ru.bugrimov.windtenants.api.v1.apiV1Mapper
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseV1SerializationTest {

    private val response = CreateResponse(
        responseType = "create",
        ub = ResponseObject(
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
    fun `should serialize the response`() {
        val json = apiV1Mapper.writeValueAsString(response)

        assertContains(json, Regex("\"name\":\\s*\"electricity\""))
        assertContains(json, Regex("\"value\":\\s*7300"))
        assertContains(json, Regex("\"volumeForPeriod\":\\s*123"))
        assertContains(json, Regex("\"accruedSum\":\\s*845.5"))
        assertContains(json, Regex("\"paidAmount\":\\s*800"))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun `should deserialize the response`() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as CreateResponse

        assertEquals(response, obj)
    }
}
