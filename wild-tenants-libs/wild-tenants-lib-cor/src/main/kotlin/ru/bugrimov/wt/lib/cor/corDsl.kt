package ru.bugrimov.wt.lib.cor

import ru.bugrimov.wt.lib.cor.handlers.CorChainDsl
import ru.bugrimov.wt.lib.cor.handlers.CorWorkerDsl

@CorDslMarker
interface ICorExecDsl<T> {
    var title: String
    var description: String
    fun on(function: suspend T.() -> Boolean)
    fun except(function: suspend T.(e: Throwable) -> Unit)

    fun build(): ICorExec<T>
}

@CorDslMarker
interface ICorChainDsl<T> : ICorExecDsl<T> {
    fun add(worker: ICorExecDsl<T>)
}

@CorDslMarker
interface ICorWorkerDsl<T> : ICorExecDsl<T> {
    fun handle(function: suspend T.() -> Unit)
}

/**
 * Точка входа в dsl построения цепочек.
 * Элементы исполняются последовательно.
 *
 * Пример:
 * ```
 *  rootChain<SomeContext> {
 *      worker {
 *      }
 *      chain {
 *          worker(...) {
 *          }
 *          worker(...) {
 *          }
 *      }
 *  }
 * ```
 */
fun <T> rootChain(function: ICorChainDsl<T>.() -> Unit): CorChainDsl<T> = CorChainDsl<T>().apply(function)


/**
 * Создает цепочку, элементы которой исполняются последовательно.
 */
fun <T> ICorChainDsl<T>.chain(function: ICorChainDsl<T>.() -> Unit) {
    add(CorChainDsl<T>().apply(function))
}

/**
 * Создает рабочего
 */
fun <T> ICorChainDsl<T>.worker(function: ICorWorkerDsl<T>.() -> Unit) {
    add(CorWorkerDsl<T>().apply(function))
}

/**
 * Создает рабочего с on и except по умолчанию
 */
fun <T> ICorChainDsl<T>.worker(
    title: String,
    description: String = "",
    blockHandle: T.() -> Unit
) {
    add(CorWorkerDsl<T>().also {
        it.title = title
        it.description = description
        it.handle(blockHandle)
    })
}
