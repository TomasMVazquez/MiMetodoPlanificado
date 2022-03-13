package com.applications.toms.mimetodoplanificado.ui.screen.mymethod

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.applications.toms.domain.CalendarMonth
import com.applications.toms.mimetodoplanificado.ui.components.customcalendar.Calendar
import com.applications.toms.mimetodoplanificado.ui.components.generics.GenericSpacer
import com.applications.toms.mimetodoplanificado.ui.components.generics.SpacerType
import com.applications.toms.mimetodoplanificado.ui.theme.VividRaspberry
import com.applications.toms.mimetodoplanificado.ui.utils.toCalendarMonth
import java.time.LocalDate

@Composable
fun MyMethod() {
    val from = LocalDate.now()
    val to = from.plusDays(28)
    val monthFrom = from.toCalendarMonth()
    val monthTo = to.toCalendarMonth()

    val calendarYear = if (monthFrom.monthNumber == monthTo.monthNumber)
        listOf(monthFrom) else listOf(monthFrom, monthTo)

    MyMethodContent(calendarYear, from, to)
}

@Composable
fun MyMethodContent(calendarYear: List<CalendarMonth>, from: LocalDate, to: LocalDate) {
    Column {

        GenericSpacer(
            type = SpacerType.HORIZONTAL,
            padding = 2.dp,
            backgroundColor = VividRaspberry
        )

        Calendar(
            calendarYear = calendarYear,
            from = from,
            to = to
        )
    }
}