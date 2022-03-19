package com.applications.toms.mimetodoplanificado.ui.components.customcalendar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.applications.toms.domain.CalendarMonth
import com.applications.toms.domain.CalendarWeek
import com.applications.toms.domain.enums.DaySelectedStatus
import java.time.LocalDate

@Composable
fun Week(
    month: CalendarMonth,
    week: CalendarWeek,
    from: LocalDate,
    to: LocalDate
) {
    val today = LocalDate.now()

    Row(modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)) {
        for (day in week) {
            Day(
                if (from.month.value == to.month.value) {
                    when {
                        day.value.isEmpty() -> day
                        day.value.toInt() == from.dayOfMonth -> day.apply { status = DaySelectedStatus.FirstDay }
                        day.value.toInt() > from.dayOfMonth && day.value.toInt() < to.dayOfMonth -> day.apply { status = DaySelectedStatus.Selected }
                        day.value.toInt() == to.dayOfMonth -> day.apply { status = DaySelectedStatus.LastDay }
                        else -> day
                    }
                } else {
                    when {
                        day.value.isEmpty() -> day
                        month.monthNumber == from.monthValue && day.value.toInt() == from.dayOfMonth -> day.apply { status = DaySelectedStatus.FirstDay }
                        month.monthNumber == from.monthValue && day.value.toInt() > from.dayOfMonth -> day.apply { status = DaySelectedStatus.Selected }
                        month.monthNumber == to.monthValue && day.value.toInt() < to.dayOfMonth -> day.apply { status = DaySelectedStatus.Selected }
                        month.monthNumber == to.monthValue && day.value.toInt() == to.dayOfMonth -> day.apply { status = DaySelectedStatus.LastDay }
                        else -> day
                    }
                },
                if (day.value.isNotEmpty()) day.value.toInt() == today.dayOfMonth && today.month.value == month.monthNumber else false
            )
        }
    }
}
