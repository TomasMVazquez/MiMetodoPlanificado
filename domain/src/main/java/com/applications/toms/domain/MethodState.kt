package com.applications.toms.domain

import java.time.LocalDate

data class MethodState(
    val methodChosen: UserAction = UserAction.NONE,
    val startDate: LocalDate = LocalDate.now()
)
