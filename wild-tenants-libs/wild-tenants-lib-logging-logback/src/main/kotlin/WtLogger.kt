package ru.bugrimov.wt.logging

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import ru.bugrimov.wt.logging.common.ILogWrapper
import kotlin.reflect.KClass

fun loggerLogback(logger: Logger): ILogWrapper = WtLogWrapperLogback(
    logger = logger,
    loggerId = logger.name,
)

fun loggerLogback(clazz: KClass<*>): ILogWrapper = loggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)

fun loggerLogback(loggerId: String): ILogWrapper = loggerLogback(LoggerFactory.getLogger(loggerId) as Logger)
