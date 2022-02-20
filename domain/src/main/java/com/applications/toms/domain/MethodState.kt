package com.applications.toms.domain

import java.time.LocalDate

data class MethodState(
    val methodChosen: Method? = null,
    val startDate: LocalDate = LocalDate.now()
)
