package websocket

import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import kotlinx.coroutines.withTimeout
import ru.bugrimov.wild_tenants.be.api.v1.models.*
import ru.bugrimov.wt.common.WtCorSettings
import ru.bugrimov.wt.ktor.WtAppSettings
import ru.bugrimov.wt.ktor.module
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class WebsocketTest {

    @Test
    fun `should create ub`() {
        val request = CreateRequest(
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
        )

        testMethod<IResponse>(request) {
            assertEquals(ResponseResult.SUCCESS, it.result)
        }
    }

    @Test
    fun `should read ub`() {
        val request = ReadRequest(
            ub = ReadObject("123"),
            debug = UbDebug(
                mode = RequestDebugMode.STUB,
                stub = RequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals(ResponseResult.SUCCESS, it.result)
        }
    }

    @Test
    fun `should update ub`() {
        val request = UpdateRequest(
            ub = UpdateObject(
                id = "123",
                meterReadings = listOf(
                    MeterReading(name = MeterReading.Name.HOT_WATER, value = BigDecimal(500))
                ),
                period = Period(Period.Month.DEC, 2024)
            ),
            debug = UbDebug(
                mode = RequestDebugMode.STUB,
                stub = RequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals(ResponseResult.SUCCESS, it.result)
        }
    }

    @Test
    fun `should delete ub`() {
        val request = DeleteRequest(
            ub = DeleteObject(
                id = "123",
            ),
            debug = UbDebug(
                mode = RequestDebugMode.STUB,
                stub = RequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals(ResponseResult.SUCCESS, it.result)
        }
    }

    @Test
    fun `should search ub`() {
        val request = SearchRequest(
            ubFilter = SearchFilter(),
            debug = UbDebug(
                mode = RequestDebugMode.STUB,
                stub = RequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals(ResponseResult.SUCCESS, it.result)
        }
    }

    private inline fun <reified T> testMethod(
        request: IRequest,
        crossinline assertBlock: (T) -> Unit
    ) = testApplication {
        application { module(WtAppSettings(corSettings = WtCorSettings())) }
        val client = createClient {
            install(WebSockets) {
                contentConverter = JacksonWebsocketContentConverter()
            }
        }

        client.webSocket("/v1/ws") {
            withTimeout(3000) {
                val response = receiveDeserialized<IResponse>() as T
                assertIs<InitResponse>(response)
            }
            sendSerialized(request)
            withTimeout(3000) {
                val response = receiveDeserialized<IResponse>() as T
                assertBlock(response)
            }
        }
    }
}
