package ru.bugrimov.windtenants.common.models

data class WtUb(
    var id: WtUbId = WtUbId.NONE,
    var lock: WtUbLock = WtUbLock.NONE,
    var ownerId: WtUserId = WtUserId.NONE,
    val ubMeterReadings: List<UbMeterReading> = mutableListOf(),
    val ubPeriod: UbPeriod = UbPeriod(),
    val permissionsClient: MutableSet<WtUbPermissionClient> = mutableSetOf()
) {
    fun isEmpty() = this == NONE

    companion object {
        val NONE = WtUb()
    }
}
