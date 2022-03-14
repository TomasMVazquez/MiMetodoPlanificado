package com.applications.toms.mimetodoplanificado.ui.components.customcalendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.applications.toms.domain.CalendarMonth
import com.applications.toms.domain.CalendarYear
import com.applications.toms.mimetodoplanificado.R
import java.time.LocalDate

@Composable
fun Calendar(
    calendarYear: CalendarYear,
    from: LocalDate,
    to: LocalDate,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier) {
        for (month in calendarYear) {
            itemsCalendarMonth(
                month = month,
                from = from,
                to = to
            )
        }
    }
}

private fun LazyListScope.itemsCalendarMonth(
    month: CalendarMonth,
    from: LocalDate,
    to: LocalDate
) {
    item {
        Spacer(Modifier.width(dimensionResource(id = R.dimen.spacer_small)))
    }
    item {
        Column {
            MonthHeader(
                month = month.name,
                year = month.year
            )

            val contentModifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)

            DaysOfWeek(modifier = contentModifier)

            month.weeks.value.forEachIndexed { index, week ->
                LazyColumn {
                    item(key = "${month.year}/${month.monthNumber}/${index + 1}") {
                        Week(
                            modifier = contentModifier,
                            week = week,
                            month = month,
                            from = from,
                            to = to
                        )
                    }
                    item {
                        Spacer(Modifier.height(dimensionResource(id = R.dimen.spacer_small)))
                    }
                }
            }
        }
    }
    item {
        Spacer(Modifier.width(dimensionResource(id = R.dimen.spacer_small)))
    }
}




