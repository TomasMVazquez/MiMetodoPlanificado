package com.applications.toms.domain

import java.time.LocalDate

data class PainScaleModel(
    val date: LocalDate,
    val painScale: Int,
    val dayOfCycle: Int
)