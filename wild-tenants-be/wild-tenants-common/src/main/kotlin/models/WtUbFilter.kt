package ru.bugrimov.wt.common.models

data class WtUbFilter(
    var searchString: String = "",
    var ownerId: WtUserId = WtUserId.NONE,
    val period: UbPeriod = UbPeriod()
) {

    fun deepCopy(): WtUbFilter = copy()
}
