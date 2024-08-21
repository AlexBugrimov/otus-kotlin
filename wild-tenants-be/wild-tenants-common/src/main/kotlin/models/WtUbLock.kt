package ru.bugrimov.wt.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class WtUbLock(private val lock: String) {

    fun asString() = lock

    companion object {
        val NONE = WtUbLock("")
    }
}
