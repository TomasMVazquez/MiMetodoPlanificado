package com.applications.toms.domain

import com.applications.toms.domain.enums.DaySelectedStatus

data class CalendarDay(val value: String, var status: DaySelectedStatus)
