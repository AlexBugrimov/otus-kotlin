package ru.bugrimov.wt.mappers

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import ru.bugrimov.wild_tenants.be.api.v1.models.*
import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.models.*
import java.math.BigDecimal
import java.time.Month

class ResponseMappersTest : StringSpec({

    "should convert the context into create response" {
        val context = WtContext(
            command = WtCommand.CREATE,
            state = WtState.RUNNING,
            workMode = WtWorkMode.TEST,
            response = WtUb(
                id = WtUbId("WT-1234"),
                ownerId = WtUserId("USER-2323"),
                ubPeriod = UbPeriod(Month.MAY, 2024),
                ubMeterReadings = listOf(
                    UbMeterReading(
                        name = MeterReadingName.ELECTRICITY,
                        indicatedValue = BigDecimal(3021)
                    ),
                    UbMeterReading(
                        name = MeterReadingName.COLD_WATER,
                        indicatedValue = BigDecimal(380)
                    ),
                    UbMeterReading(
                        name = MeterReadingName.HOT_WATER,
                        indicatedValue = BigDecimal(220)
                    )
                )
            )
        )

        val response = context.toTransportUb()

        response.shouldBeTypeOf<CreateResponse> {
            it.result.shouldNotBeNull {
                shouldBeEqual(ResponseResult.SUCCESS)
            }
            it.ub.shouldNotBeNull {
                meterReadings.shouldNotBeNull().shouldHaveSize(3)
                period shouldBe Period(Period.Month.MAY, 2024)
            }
        }
    }

    "should convert the context into read response" {
        val context = WtContext(
            command = WtCommand.READ,
            state = WtState.RUNNING,
            workMode = WtWorkMode.TEST,
            response = WtUb(
                id = WtUbId("WT-1234"),
                ownerId = WtUserId("USER-2323"),
                ubPeriod = UbPeriod(Month.MAY, 2024),
                ubMeterReadings = listOf(
                    UbMeterReading(name = MeterReadingName.ELECTRICITY, indicatedValue = BigDecimal(3021)),
                    UbMeterReading(name = MeterReadingName.COLD_WATER, indicatedValue = BigDecimal(380))
                )
            )
        )

        val response = context.toTransportUb()

        response.shouldBeTypeOf<ReadResponse> {
            it.result.shouldNotBeNull {
                shouldBeEqual(ResponseResult.SUCCESS)
            }
            it.ub.shouldNotBeNull {
                meterReadings.shouldNotBeNull().shouldHaveSize(2)
                period shouldBe Period(Period.Month.MAY, 2024)
            }
        }
    }

    "should convert the context into update response" {
        val context = WtContext(
            command = WtCommand.UPDATE,
            state = WtState.RUNNING,
            workMode = WtWorkMode.TEST,
            response = WtUb(
                id = WtUbId("WT-1234"),
                ownerId = WtUserId("USER-2323"),
                ubPeriod = UbPeriod(Month.MAY, 2024),
                ubMeterReadings = listOf(
                    UbMeterReading(
                        name = MeterReadingName.COLD_WATER,
                        indicatedValue = BigDecimal(400)
                    )
                )
            )
        )

        val response = context.toTransportUb()
        response.shouldBeTypeOf<UpdateResponse> {
            it.result.shouldNotBeNull {
                shouldBeEqual(ResponseResult.SUCCESS)
            }
            it.ub.shouldNotBeNull {
                meterReadings.shouldNotBeNull().shouldHaveSize(1)
                period shouldBe Period(Period.Month.MAY, 2024)
            }
        }
    }

    "should convert the context into delete response" {
        val context = WtContext(
            command = WtCommand.DELETE,
            state = WtState.RUNNING,
            workMode = WtWorkMode.TEST,
            response = WtUb(
                id = WtUbId("WT-1234"),
                ownerId = WtUserId("USER-2323"),
                ubPeriod = UbPeriod(Month.MAY, 2024),
                ubMeterReadings = listOf(
                    UbMeterReading(
                        name = MeterReadingName.COLD_WATER,
                        indicatedValue = BigDecimal(20)
                    )
                )
            )
        )

        val response = context.toTransportUb()

        response.shouldBeTypeOf<DeleteResponse> {
            it.result.shouldNotBeNull {
                shouldBeEqual(ResponseResult.SUCCESS)
            }
            it.ub.shouldNotBeNull {
                meterReadings.shouldNotBeNull().shouldHaveSize(1)
                period shouldBe Period(Period.Month.MAY, 2024)
            }
        }
    }

    "should convert the context into search response" {
        val context = WtContext(
            command = WtCommand.SEARCH,
            state = WtState.RUNNING,
            workMode = WtWorkMode.TEST,
            ubsResponse = mutableListOf(
                WtUb(
                    id = WtUbId("WT-1234"),
                    ownerId = WtUserId("USER-2323"),
                    ubPeriod = UbPeriod(Month.MAY, 2024),
                    ubMeterReadings = listOf(
                        UbMeterReading(
                            name = MeterReadingName.COLD_WATER,
                            indicatedValue = BigDecimal(20)
                        )
                    )
                ),
                WtUb(
                    id = WtUbId("WT-432"),
                    ownerId = WtUserId("USER-21"),
                    ubPeriod = UbPeriod(Month.JANUARY, 2024),
                    ubMeterReadings = listOf(
                        UbMeterReading(
                            name = MeterReadingName.ELECTRICITY,
                            indicatedValue = BigDecimal(400)
                        )
                    )
                )
            )
        )

        val response = context.toTransportUb()

        response.shouldBeTypeOf<SearchResponse> {
            it.result.shouldNotBeNull {
                shouldBeEqual(ResponseResult.SUCCESS)
            }
            it.ubs.shouldNotBeNull().shouldHaveSize(2)
        }
    }

    "should convert the context into init response" {
        val context = WtContext(
            command = WtCommand.INIT,
            state = WtState.RUNNING,
            workMode = WtWorkMode.TEST,
            response = WtUb(
                id = WtUbId("WT-1234"),
                ownerId = WtUserId("USER-2323"),
                ubPeriod = UbPeriod(Month.MAY, 2024),
                ubMeterReadings = listOf(
                    UbMeterReading(
                        name = MeterReadingName.COLD_WATER,
                        indicatedValue = BigDecimal(20)
                    )
                )
            )
        )

        val response = context.toTransportUb()

        response.shouldBeTypeOf<InitResponse> {
            it.result.shouldNotBeNull {
                shouldBeEqual(ResponseResult.SUCCESS)
            }
            it.errors.shouldBeNull()
        }
    }
})