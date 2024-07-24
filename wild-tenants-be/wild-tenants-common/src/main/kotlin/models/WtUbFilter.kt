package ru.bugrimov.windtenants.common.models

data class WtUbFilter(
    var searchString: String = "",
    var ownerId: WtUserId = WtUserId.NONE
)
