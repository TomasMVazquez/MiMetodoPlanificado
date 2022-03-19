package com.applications.toms.mimetodoplanificado.ui.screen.mymethod

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.CircularDaysProgress
import com.applications.toms.mimetodoplanificado.ui.components.customcalendar.Calendar
import com.applications.toms.mimetodoplanificado.ui.components.generics.GenericSpacer
import com.applications.toms.mimetodoplanificado.ui.components.generics.SpacerType
import com.applications.toms.mimetodoplanificado.ui.screen.mymethod.MyMethodViewModel.State
import com.applications.toms.mimetodoplanificado.ui.utils.safeLet
import com.applications.toms.mimetodoplanificado.ui.utils.toCalendarMonth
import java.time.LocalDate

@Composable
fun MyMethod(viewModel: MyMethodViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState(State())

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        if (!state.loading) MyMethodContent(state)
    }

}

@Composable
fun MyMethodContent(state: State) {
    safeLet(state.startDate, state.endDate) { from, to ->
        val monthFrom = from.toCalendarMonth()
        val monthTo = to.toCalendarMonth()
        val calendarYear = if (monthFrom.monthNumber == monthTo.monthNumber)
            listOf(monthFrom) else listOf(monthFrom, monthTo)

        val totalDays = to.compareTo(from).toFloat()
        val currentDay = LocalDate.now().compareTo(from).toFloat()

        Column(modifier = Modifier.fillMaxSize()) {

            CircularDaysProgress(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(dimensionResource(id = R.dimen.padding_xlarge)),
                percentage = currentDay.div(totalDays),
                number = totalDays.toInt()
            )

            GenericSpacer(
                type = SpacerType.VERTICAL,
                padding = dimensionResource(id = R.dimen.spacer_medium)
            )

            Calendar(
                modifier = Modifier.fillMaxWidth(),
                calendarYear = calendarYear,
                from = from,
                to = to
            )

        }
    }
}

