package com.applications.toms.mimetodoplanificado.ui.utils

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import com.applications.toms.domain.CalendarMonth
import com.applications.toms.domain.enums.DaySelectedStatus
import com.applications.toms.domain.getFirstDayOfMonth
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

const val FORMATTED_DATE_PATTERN = "dd/MM/yy"

fun LocalDate.toFormattedString(): String = format(DateTimeFormatter.ofPattern(FORMATTED_DATE_PATTERN))

fun DaySelectedStatus.color(theme: Colors): Color = when (this) {
    DaySelectedStatus.Selected -> theme.secondary
    else -> Color.Transparent
}

fun DaySelectedStatus.isMarked(): Boolean = when (this) {
        DaySelectedStatus.Selected -> true
        DaySelectedStatus.FirstDay -> true
        DaySelectedStatus.LastDay -> true
        DaySelectedStatus.FirstLastDay -> true
        else -> false
    }

fun LocalDate.toCalendarMonth(): CalendarMonth =
    CalendarMonth(
        name = this.month.getDisplayName(TextStyle.FULL, Locale.getDefault()).uppercase(),
        year = Year.now().toString(),
        numDays = this.month.length(Year.isLeap(this.year.toLong())),
        monthNumber = this.monthValue,
        startDayOfWeek = getFirstDayOfMonth(YearMonth.of(this.year,this.month))
    )