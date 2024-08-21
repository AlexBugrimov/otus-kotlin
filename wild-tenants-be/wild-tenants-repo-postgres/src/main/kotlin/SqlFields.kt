package ru.bugrimov.wt.backend.repo.postgresql

object SqlFields {
    // utility_bill
    const val UB_ID = "ub_id"
    const val OWNER_ID = "owner_id"
    const val LOCK = "lock"
    const val MONTH = "month"
    const val YEAR = "year"

    // meter_reading
    const val MR_ID = "mr_id"
    const val NAME = "name"
    const val INDICATED_VALUE = "indicated_value"
    const val VOLUME_FOR_PERIOD = "volume_for_period"
    const val ACCRUED_SUM = "accrued_sum"
    const val PAID_AMOUNT = "paid_amount"

    const val UB_MONTH_TYPE = "ub_month_type"
    const val MR_NAME_TYPE = "mr_name_type"


    fun String.quoted() = "\"$this\""

    val allFields = listOf(
        UB_ID, MONTH, YEAR, MR_ID, NAME, LOCK, OWNER_ID, INDICATED_VALUE, VOLUME_FOR_PERIOD, ACCRUED_SUM, PAID_AMOUNT
    )
}
