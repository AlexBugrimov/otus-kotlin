package rest

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import ru.bugrimov.wild_tenants.be.api.v1.models.*
import ru.bugrimov.wt.common.WtCorSettings
import ru.bugrimov.wt.ktor.WtAppSettings
import ru.bugrimov.wt.ktor.module
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals

class UbV1ApiTest {

    @Test
    fun `should create ub`() = testApplication(
        func = "create",
        request = CreateRequest(
            ub = CreateObject(
                meterReadings = listOf(
                    MeterReading(name = MeterReading.Name.HOT_WATER, value = BigDecimal(200)),
                    MeterReading(name = MeterReading.Name.COLD_WATER, value = BigDecimal(250)),
                    MeterReading(name = MeterReading.Name.ELECTRICITY, value = BigDecimal(300)),
                ),
                period = Period(Period.Month.MAR, 2024)
            ),
            debug = UbDebug(
                mode = RequestDebugMode.STUB,
                stub = RequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<CreateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ub?.id)
    }

    @Test
    fun `should read ub`() = testApplication(
        func = "read",
        request = ReadRequest(
            ub = ReadObject("666"),
            debug = UbDebug(
                mode = RequestDebugMode.STUB,
                stub = RequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<ReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ub?.id)
    }

    @Test
    fun `should update ub`() = testApplication(
        func = "update",
        request = UpdateRequest(
            ub = UpdateObject(
                meterReadings = listOf(
                    MeterReading(name = MeterReading.Name.HOT_WATER, value = BigDecimal(200)),
                    MeterReading(name = MeterReading.Name.COLD_WATER, value = BigDecimal(250)),
                    MeterReading(name = MeterReading.Name.ELECTRICITY, value = BigDecimal(300)),
                ),
                period = Period(Period.Month.MAR, 2024)
            ),
            debug = UbDebug(
                mode = RequestDebugMode.STUB,
                stub = RequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<UpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ub?.id)
    }

    @Test
    fun `should delete ub`() = testApplication(
        func = "delete",
        request = DeleteRequest(
            ub = DeleteObject(
                id = "666",
            ),
            debug = UbDebug(
                mode = RequestDebugMode.STUB,
                stub = RequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<DeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.ub?.id)
    }

    @Test
    fun `should search ub`() = testApplication(
        func = "search",
        request = SearchRequest(
            ubFilter = SearchFilter(),
            debug = UbDebug(
                mode = RequestDebugMode.STUB,
                stub = RequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<SearchResponse>()
        assertEquals(200, response.status.value)
        assertEquals("1", responseObj.ubs?.first()?.id)
    }

    private fun testApplication(
        func: String,
        request: IRequest,
        function: suspend (HttpResponse) -> Unit,
    ): Unit = testApplication {
        application { module(WtAppSettings(corSettings = WtCorSettings())) }
        val client = createClient {
            install(ContentNegotiation) {
                jackson {
                    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                    enable(SerializationFeature.INDENT_OUTPUT)
                    writerWithDefaultPrettyPrinter()
                }
            }
        }
        val response = client.post("/v1/ub/$func") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        function(response)
    }
}
