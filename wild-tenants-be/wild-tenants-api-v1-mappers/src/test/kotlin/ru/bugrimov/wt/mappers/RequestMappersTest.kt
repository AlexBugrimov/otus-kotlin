package ru.bugrimov.wt.mappers

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainOnly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlinx.datetime.Month
import ru.bugrimov.wild_tenants.be.api.v1.models.*
import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.models.*
import ru.bugrimov.wt.common.stubs.WtStubs
import java.math.BigDecimal

class RequestMappersTest : StringSpec({

    val ubDebug = UbDebug(mode = RequestDebugMode.STUB, stub = RequestDebugStubs.SUCCESS)

    "should convert the creation request into a context" {
        val createRequest = CreateRequest(
            debug = ubDebug,
            ub = CreateObject(
                meterReadings = listOf(
                    MeterReading(name = MeterReading.Name.HOT_WATER, value = BigDecimal(200)),
                    MeterReading(name = MeterReading.Name.COLD_WATER, value = BigDecimal(300)),
                    MeterReading(name = MeterReading.Name.ELECTRICITY, value = BigDecimal(7300))
                ),
                period = Period(Period.Month.MAR, year = 2024)
            ),
        )

        val context = WtContext()
        context.fromTransport(createRequest)

        context.shouldNotBeNull {
            stubCase shouldBeEqual WtStubs.SUCCESS
            workMode shouldBeEqual WtWorkMode.STUB
            request.shouldNotBeNull {
                id shouldBe WtUbId.NONE
                ubPeriod shouldBe UbPeriod(month = Month.MARCH, year = 2024)
                ubMeterReadings shouldHaveSize 3
                ubMeterReadings.shouldContainOnly(
                    UbMeterReading(
                        name = MeterReadingName.HOT_WATER,
                        indicatedValue = BigDecimal(200),
                        volumeForPeriod = null,
                        accruedSum = null,
                        paidAmount = null
                    ),
                    UbMeterReading(
                        name = MeterReadingName.COLD_WATER,
                        indicatedValue = BigDecimal(300),
                        volumeForPeriod = null,
                        accruedSum = null,
                        paidAmount = null
                    ),
                    UbMeterReading(
                        name = MeterReadingName.ELECTRICITY,
                        indicatedValue = BigDecimal(7300),
                        volumeForPeriod = null,
                        accruedSum = null,
                        paidAmount = null
                    ),
                )
            }
        }
    }

    "should convert the read request into a context" {
        val readRequest = ReadRequest(
            debug = ubDebug,
            ub = ReadObject(id = "UR-123456")
        )

        val context = WtContext()
        context.fromTransport(readRequest)
        val request = context.request
        request.shouldNotBeNull()
        request.id shouldBe WtUbId("UR-123456")
    }

    "should convert the update request into a context" {
        val updateRequest = UpdateRequest(
            debug = ubDebug,
            ub = UpdateObject(
                id = "UR-121212",
                lock = "V2-07-2024",
                meterReadings = listOf(MeterReading(name = MeterReading.Name.COLD_WATER, value = BigDecimal(301)))
            )
        )

        val context = WtContext()
        context.fromTransport(updateRequest)

        val request = context.request
        request.shouldNotBeNull()
        request.id shouldBe WtUbId("UR-121212")
        request.lock shouldBe WtUbLock("V2-07-2024")
        request.ubMeterReadings shouldHaveSize 1
        request.ubMeterReadings.shouldContainOnly(
            UbMeterReading(
                name = MeterReadingName.COLD_WATER,
                indicatedValue = BigDecimal(301),
                volumeForPeriod = null,
                accruedSum = null,
                paidAmount = null
            )
        )
    }

    "should convert the delete request into a context" {
        val deleteRequest = DeleteRequest(
            debug = ubDebug,
            ub = DeleteObject(
                id = "UR-131313",
                lock = "V-1-07-2024"
            )
        )

        val context = WtContext()
        context.fromTransport(deleteRequest)

        val request = context.request
        request.shouldNotBeNull()
        request.id shouldBe WtUbId("UR-131313")
        request.lock shouldBe WtUbLock("V-1-07-2024")
    }

    "should convert the search request into a context" {
        val searchRequest = SearchRequest(
            debug = ubDebug,
            ubFilter = SearchFilter(
                searchString = "some string",
                ownerId = "USER-01",
            )
        )

        val context = WtContext()
        context.fromTransport(searchRequest)

        context.filterRequest shouldBe WtUbFilter("some string", ownerId = WtUserId("USER-01"))
    }
})