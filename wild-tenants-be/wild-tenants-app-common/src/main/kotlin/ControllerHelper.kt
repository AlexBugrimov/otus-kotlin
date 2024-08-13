package ru.bugrimov.wt.app.common

import kotlinx.datetime.Clock
import ru.bugrimov.wt.common.WtContext
import ru.bugrimov.wt.common.models.WtCommand
import ru.bugrimov.wt.api.log1.mapper.toLog
import ru.bugrimov.wt.common.helpers.asWtError
import ru.bugrimov.wt.common.models.WtState
import kotlin.reflect.KClass

suspend inline fun <T> AppSettings.controllerHelper(
    crossinline getRequest: suspend WtContext.() -> Unit,
    crossinline toResponse: suspend WtContext.() -> T,
    clazz: KClass<*>,
    logId: String,
): T {
    val logger = corSettings.loggerProvider.logger(clazz)
    val ctx = WtContext(
        timeStart = Clock.System.now(),
    )
    return try {
        ctx.getRequest()
        logger.info(
            msg = "Request $logId started for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        processor.exec(ctx)
        logger.info(
            msg = "Request $logId processed for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        ctx.toResponse()
    } catch (e: Throwable) {
        logger.error(
            msg = "Request $logId failed for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId),
            e = e,
        )
        ctx.state = WtState.FAILING
        ctx.errors.add(e.asWtError())
        processor.exec(ctx)
        if (ctx.command == WtCommand.NONE) {
            ctx.command = WtCommand.READ
        }
        ctx.toResponse()
    }
}
