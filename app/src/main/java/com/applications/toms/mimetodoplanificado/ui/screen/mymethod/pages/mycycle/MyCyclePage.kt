package com.applications.toms.mimetodoplanificado.ui.screen.mymethod.pages.mycycle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.CircularDaysProgress
import com.applications.toms.mimetodoplanificado.ui.components.EmptyStateComponent
import com.applications.toms.mimetodoplanificado.ui.components.customcalendar.Calendar
import com.applications.toms.mimetodoplanificado.ui.components.generics.ButtonType
import com.applications.toms.mimetodoplanificado.ui.components.generics.GenericButton
import com.applications.toms.mimetodoplanificado.ui.screen.mymethod.MyMethodViewModel
import com.applications.toms.mimetodoplanificado.ui.screen.mymethod.pages.mycycle.MyCycleViewModel.*
import com.applications.toms.mimetodoplanificado.ui.utils.safeLet
import com.applications.toms.mimetodoplanificado.ui.utils.toCalendarMonth
import java.time.LocalDate


@Composable
fun MyCyclePage(
    viewModel: MyCycleViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState(State())

    MyCycleContent(state) {
        viewModel.saveMyCycle()
    }
}

@Composable
fun MyCycleContent(
    state: State,
    onRegisterPeriod: () -> Unit
) {

    safeLet(state.startDate, state.endDate) { from, to ->
        val monthFrom = from.toCalendarMonth()
        val monthTo = to.toCalendarMonth()
        val calendarYear = if (monthFrom.monthNumber == monthTo.monthNumber)
            listOf(monthFrom) else listOf(monthFrom, monthTo)

        val totalDays = (from.until(to).days + 1).toFloat()
        val currentDay = (from.until(LocalDate.now()).days + 1).toFloat()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            /**
             * Title
             */
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(id = R.dimen.padding_large)),
                text = stringResource(id = R.string.my_cycle_page_title),
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center
            )

            if (state.hasCycleConfigured) {
                /**
                 * Progress Day
                 */
                CircularDaysProgress(
                    modifier = Modifier
                        .weight(1f)
                        .padding(dimensionResource(id = R.dimen.padding_medium)),
                    percentage = currentDay.div(totalDays),
                    number = totalDays.toInt()
                )
            } else {
                EmptyStateComponent()
            }
            /**
             * Info
             */
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.padding_large)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
                    text = when {
                        !state.hasCycleConfigured -> stringResource(R.string.my_cycle_empty_info)
                        else -> ""
                    },
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center
                )
                GenericButton(
                    buttonType = ButtonType.HIGH_EMPHASIS,
                    text = stringResource(id = R.string.register_my_period)
                ) {
                    onRegisterPeriod()
                }
            }
            /**
             * Calendar
             */
            Calendar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_medium)),
                calendarYear = calendarYear,
                from = from,
                to = to,
                breakDays = 0
            )
        }
    }
}
