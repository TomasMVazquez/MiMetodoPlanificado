package com.applications.toms.domain

import java.time.LocalDate

data class MyCycle(
    val id: Int? = null,
    val startDate: LocalDate,
    val nextCycle: LocalDate,
    val totalDaysCycle: Long
)