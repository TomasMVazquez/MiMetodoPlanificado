package com.applications.toms.domain

import com.applications.toms.domain.enums.DaySelectedStatus
import java.text.DateFormatSymbols
import java.time.YearMonth

typealias CalendarYear = List<CalendarMonth>

typealias CalendarWeek = List<CalendarDay>

fun daysOfWeek(): List<String> = DateFormatSymbols().weekdays.filterNot { it.isEmpty() }.map { it.uppercase() }

fun getFirstDayOfMonth(yearMonth: YearMonth): String {
    val firstDay = yearMonth.atDay(1).dayOfWeek.ordinal + 1
    return daysOfWeek()[firstDay]
}

data class CalendarMonth(
    val name: String,
    val year: String,
    val numDays: Int,
    val monthNumber: Int,
    val startDayOfWeek: String
) {
    private val days = mutableListOf<CalendarDay>().apply {
        for (i in 1..daysOfWeek().indexOf(startDayOfWeek)) {
            add(
                CalendarDay(
                    "",
                    DaySelectedStatus.NonClickable
                )
            )
        }
        for (i in 1..numDays) {
            add(
                CalendarDay(
                    i.toString(),
                    DaySelectedStatus.NoSelected
                )
            )
        }
    }.toList()

    fun getDay(day: Int): CalendarDay {
        return days[day + daysOfWeek().indexOf(startDayOfWeek) - 1]
    }

    fun getPreviousDay(day: Int): CalendarDay? {
        if (day <= 1) return null
        return getDay(day - 1)
    }

    fun getNextDay(day: Int): CalendarDay? {
        if (day >= numDays) return null
        return getDay(day + 1)
    }

    val weeks = lazy { days.chunked(7).map { completeWeek(it) } }

    private fun completeWeek(list: List<CalendarDay>): List<CalendarDay> {
        var gapsToFill = 7 - list.size

        return if (gapsToFill != 0) {
            val mutableList = list.toMutableList()
            while (gapsToFill > 0) {
                mutableList.add(
                    CalendarDay(
                        "",
                        DaySelectedStatus.NonClickable
                    )
                )
                gapsToFill--
            }
            mutableList
        } else {
            list
        }
    }
}

























