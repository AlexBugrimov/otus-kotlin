package ru.bugrimov.wt.biz.exceptions

import ru.bugrimov.wt.common.models.WtWorkMode


class WtUbDbNotConfiguredException(val workMode: WtWorkMode) : Exception(
    "Database is not configured properly for work-mode $workMode"
)
