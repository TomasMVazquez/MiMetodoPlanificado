package com.applications.toms.domain

import com.applications.toms.domain.enums.Method
import java.time.LocalDate

data class MethodAndStartDate(
    val methodChosen: Method? = null,
    val startDate: LocalDate = LocalDate.now()
)
