package com.applications.toms.mimetodoplanificado.ui.components.customcalendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.applications.toms.domain.CalendarMonth
import com.applications.toms.domain.CalendarYear
import com.applications.toms.mimetodoplanificado.R
import java.time.LocalDate

@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    calendarYear: CalendarYear,
    from: LocalDate,
    to: LocalDate,
    breakDays: Int
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        for (month in calendarYear) {
            itemsCalendarMonth(
                month = month,
                from = from,
                to = to,
                breakDays = breakDays
            )
        }
    }
}

private fun LazyListScope.itemsCalendarMonth(
    month: CalendarMonth,
    from: LocalDate,
    to: LocalDate,
    breakDays: Int
) {
    item {
        Spacer(Modifier.width(dimensionResource(id = R.dimen.spacer_small)))
    }
    item {
        Column {
            MonthHeader(
                modifier = Modifier.fillMaxWidth(),
                month = month.name,
                year = month.year
            )

            DaysOfWeek()

            month.weeks.value.forEachIndexed { _, week ->
                Week(
                    week = week,
                    month = month,
                    from = from,
                    to = to,
                    breakDays = breakDays
                )
                Spacer(Modifier.height(dimensionResource(id = R.dimen.spacer_small)))
            }
        }
    }
    item {
        Spacer(Modifier.width(dimensionResource(id = R.dimen.spacer_small)))
    }
}




