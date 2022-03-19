package com.applications.toms.mimetodoplanificado.ui.components.customcalendar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import com.applications.toms.domain.daysOfWeek

private const val FIRST_LETTER_OF_WEEK = 1

@Composable
fun DaysOfWeek() {
    Row(modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)) {
        for (day in daysOfWeek()) {
            WeekDay(name = day.take(FIRST_LETTER_OF_WEEK))
        }
    }
}