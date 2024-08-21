package ru.bugrimov.wt.common.models

data class WtUb(
    var id: WtUbId = WtUbId.NONE,
    var lock: WtUbLock = WtUbLock.NONE,
    var ownerId: WtUserId = WtUserId.NONE,
    var ubMeterReadings: List<UbMeterReading> = mutableListOf(),
    var ubPeriod: UbPeriod = UbPeriod(),
    val permissionsClient: MutableSet<WtUbPermissionClient> = mutableSetOf()
) {
    fun isEmpty() = this == NONE
    fun deepCopy(): WtUb = copy(permissionsClient = permissionsClient.toMutableSet())

    companion object {
        val NONE = WtUb()
    }
}
