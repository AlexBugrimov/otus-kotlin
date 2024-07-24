package ru.bugrimov.windtenants.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class WtUbLock(private val id: String) {

    fun asString() = id

    companion object {
        val NONE = WtUbLock("")
    }
}
