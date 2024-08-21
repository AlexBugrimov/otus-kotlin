package ru.bugrimov.wt.common.models

@JvmInline
value class WtUbId(val id: String) {

    fun asString() = id

    companion object {
        val NONE = WtUbId("")
    }
}
