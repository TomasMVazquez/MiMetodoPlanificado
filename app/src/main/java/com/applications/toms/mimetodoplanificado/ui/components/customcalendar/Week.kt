package com.applications.toms.mimetodoplanificado.ui.components.customcalendar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.applications.toms.domain.CalendarMonth
import com.applications.toms.domain.CalendarWeek
import com.applications.toms.domain.enums.DaySelectedStatus
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.utils.isMarked
import java.time.LocalDate

@Composable
fun Week(
    month: CalendarMonth,
    week: CalendarWeek,
    from: LocalDate,
    to: LocalDate
) {
    val (leftFillColor, rightFillColor) = getLeftRightWeekColors(week, month)
    val today = LocalDate.now()

    Row(modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)) {
        val spaceModifiers = Modifier
            .weight(1f)
            .heightIn(max = dimensionResource(id = R.dimen.calendar_day_size))
        Surface(modifier = spaceModifiers, color = leftFillColor) {
            Spacer(Modifier.fillMaxHeight())
        }
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
        Surface(modifier = spaceModifiers, color = rightFillColor) {
            Spacer(Modifier.fillMaxHeight())
        }
    }
}

@Composable
private fun getLeftRightWeekColors(week: CalendarWeek, month: CalendarMonth): Pair<Color, Color> {
    val materialColors = MaterialTheme.colors

    val firstDayOfTheWeek = week[0].value
    val leftFillColor = if (firstDayOfTheWeek.isNotEmpty()) {
        val lastDayPreviousWeek = month.getPreviousDay(firstDayOfTheWeek.toInt())
        if (lastDayPreviousWeek?.status?.isMarked() == true && week[0].status.isMarked()) {
            materialColors.secondary
        } else {
            Color.Transparent
        }
    } else {
        Color.Transparent
    }

    val lastDayOfTheWeek = week[6].value
    val rightFillColor = if (lastDayOfTheWeek.isNotEmpty()) {
        val firstDayNextWeek = month.getNextDay(lastDayOfTheWeek.toInt())
        if (firstDayNextWeek?.status?.isMarked() == true && week[6].status.isMarked()) {
            materialColors.secondary
        } else {
            Color.Transparent
        }
    } else {
        Color.Transparent
    }

    return leftFillColor to rightFillColor
}