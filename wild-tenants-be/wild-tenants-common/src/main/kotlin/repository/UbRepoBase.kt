package ru.bugrimov.wt.common.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import ru.bugrimov.wt.common.helpers.errorSystem
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

abstract class UbRepoBase : IRepoUb {

    protected suspend fun tryUbMethod(
        timeout: Duration = 10.seconds,
        ctx: CoroutineContext = Dispatchers.IO,
        block: suspend () -> IDbUbResponse
    ) = try {
        withTimeout(timeout) {
            withContext(ctx) {
                block()
            }
        }
    } catch (e: Throwable) {
        DbUbResponseErr(errorSystem("methodException", e = e))
    }

    protected suspend fun tryUbsMethod(block: suspend () -> IDbUbsResponse) = try {
        block()
    } catch (e: Throwable) {
        DbUbsResponseErr(errorSystem("methodException", e = e))
    }

}
