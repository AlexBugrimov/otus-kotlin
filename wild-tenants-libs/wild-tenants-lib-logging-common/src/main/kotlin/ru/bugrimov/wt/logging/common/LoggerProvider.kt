package ru.bugrimov.wt.logging.common

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

class LoggerProvider(private val provider: (String) -> ILogWrapper = { ILogWrapper.DEFAULT }) {

    fun logger(loggerId: String): ILogWrapper = provider(loggerId)

    fun logger(type: KClass<*>): ILogWrapper = provider(type.qualifiedName ?: type.simpleName ?: "(unknown)")

    fun logger(function: KFunction<*>): ILogWrapper = provider(function.name)
}