package com.applications.toms.mimetodoplanificado.ui.utils

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import com.applications.toms.domain.CalendarMonth
import com.applications.toms.domain.enums.DaySelectedStatus
import com.applications.toms.domain.getFirstDayOfMonth
import java.time.LocalDate
import java.time.LocalTime
import java.time.Year
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

const val FORMATTED_DATE_PATTERN = "dd/MM/yy"
const val FORMATTED_TIME_PATTERN = "HH:mm"

fun LocalDate.toFormattedString(): String = format(DateTimeFormatter.ofPattern(FORMATTED_DATE_PATTERN))

fun String.convertToTimeInMills(delayFiveSeconds: Boolean = false): Long =
    try {
        val myTime = LocalTime.parse(this, DateTimeFormatter.ofPattern(FORMATTED_TIME_PATTERN))
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, myTime.hour)
        if (delayFiveSeconds) {
            calendar.set(Calendar.MINUTE, myTime.minute.minus(1))
            calendar.set(Calendar.SECOND, 55)
        } else {
            calendar.set(Calendar.MINUTE, myTime.minute)
            calendar.set(Calendar.SECOND, 0)
        }
        calendar.timeInMillis
    } catch (t: Throwable) {
        0L
    }

fun DaySelectedStatus.color(theme: Colors): Color = when (this) {
    DaySelectedStatus.Selected -> theme.secondary
    DaySelectedStatus.BreakDay -> theme.secondaryVariant
    else -> Color.Transparent
}

fun DaySelectedStatus.isMarked(): Boolean = when (this) {
    DaySelectedStatus.Selected -> true
    DaySelectedStatus.FirstDay -> true
    DaySelectedStatus.LastDay -> true
    DaySelectedStatus.FirstLastDay -> true
    DaySelectedStatus.BreakDay -> true
    DaySelectedStatus.LastBreakDay -> true
    else -> false
}

fun LocalDate.toCalendarMonth(): CalendarMonth =
    CalendarMonth(
        name = this.month.getDisplayName(TextStyle.FULL, Locale.getDefault()).uppercase(),
        year = Year.now().toString(),
        numDays = this.month.length(Year.isLeap(this.year.toLong())),
        monthNumber = this.monthValue,
        startDayOfWeek = getFirstDayOfMonth(YearMonth.of(this.year, this.month))
    )

inline fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}