package com.applications.toms.mimetodoplanificado.ui.screen.mymethod.pages.mymethod

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
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
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.domain.enums.Method
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.CircularDaysProgress
import com.applications.toms.mimetodoplanificado.ui.components.EmptyStateComponent
import com.applications.toms.mimetodoplanificado.ui.components.InfoNotificationsAndAlarm
import com.applications.toms.mimetodoplanificado.ui.components.customcalendar.Calendar
import com.applications.toms.mimetodoplanificado.ui.components.customcalendar.InfoCalendar
import com.applications.toms.mimetodoplanificado.ui.components.generics.GenericSpacer
import com.applications.toms.mimetodoplanificado.ui.components.generics.SpacerType
import com.applications.toms.mimetodoplanificado.ui.screen.settings.ConfirmRebootSettings
import com.applications.toms.mimetodoplanificado.ui.screen.mymethod.pages.mymethod.MyMethodViewModel.State
import com.applications.toms.mimetodoplanificado.ui.utils.hasBeenReboot
import com.applications.toms.mimetodoplanificado.ui.utils.methods.TOTAL_CYCLE_DAYS
import com.applications.toms.mimetodoplanificado.ui.utils.safeLet
import com.applications.toms.mimetodoplanificado.ui.utils.toCalendarMonth
import com.google.accompanist.pager.ExperimentalPagerApi
import java.time.LocalDate

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun MyMethodPage(
    viewModel: MyMethodViewModel = hiltViewModel(),
    context: Context,
) {
    val state by viewModel.state.collectAsState(State())

    if (state.errorState != null) {
        EmptyStateComponent(
            textToShow = when (state.errorState!!) {
                ErrorStates.EMPTY -> stringResource(R.string.snackbar_message_error_empty)
                ErrorStates.NOT_FOUND -> stringResource(R.string.snackbar_message_error_not_found)
                ErrorStates.NOT_SAVED -> stringResource(R.string.snackbar_message_error_not_saved)
                ErrorStates.GENERIC,
                ErrorStates.THROWABLE -> stringResource(R.string.snackbar_message_error_message)
            }
        )
    } else {
        MyMethodContent(state, context) {
            viewModel.getMethodData()
        }
    }
}

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun MyMethodContent(
    state: State,
    context: Context,
    updateState: () -> Unit
) {

    state.methodChosen?.let {
        ConfirmRebootSettings(
            open = hasBeenReboot(context),
            context = context,
            methodChosen = it
        )
    }

    safeLet(state.startDate, state.endDate) { from, to ->
        val monthFrom = from.toCalendarMonth()
        val monthTo = to.toCalendarMonth()
        val calendarYear = if (monthFrom.monthNumber == monthTo.monthNumber)
            listOf(monthFrom) else listOf(monthFrom, monthTo)

        val totalDays = (from.until(to).days + 1).toFloat()
        val currentDay = (from.until(LocalDate.now()).days + 1).toFloat()
        val breakDayStarts = state.breakDays?.let { to.minusDays(it.toLong() - 1) }

        if (currentDay > TOTAL_CYCLE_DAYS) updateState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.loading) {
                CircularProgressIndicator(color = MaterialTheme.colors.secondary)
            } else {
                /**
                 * Title
                 */
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimensionResource(id = R.dimen.padding_large)),
                    text = when (state.methodChosen?.methodAndStartDate?.methodChosen) {
                        Method.PILLS -> stringResource(R.string.pills)
                        Method.RING -> stringResource(R.string.ring)
                        Method.SHOOT -> stringResource(R.string.injection)
                        Method.PATCH -> stringResource(R.string.patch)
                        else -> ""
                    },
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.onPrimary,
                    textAlign = TextAlign.Center
                )
                /**
                 * Progress Day
                 */
                CircularDaysProgress(
                    modifier = Modifier
                        .weight(1f)
                        .padding(dimensionResource(id = R.dimen.padding_medium)),
                    percentage = currentDay.div(totalDays),
                    number = totalDays.toInt(),
                    color = if (LocalDate.now() >= breakDayStarts) MaterialTheme.colors.secondaryVariant
                    else MaterialTheme.colors.secondary
                )

                GenericSpacer(
                    type = SpacerType.VERTICAL,
                    padding = dimensionResource(id = R.dimen.spacer_small)
                )
                /**
                 * Info Notif & Alarm
                 */
                InfoNotificationsAndAlarm(
                    state.isAlarmEnable,
                    state.alarmTime,
                    state.isNotificationEnable,
                    state.notificationTime
                )

                GenericSpacer(
                    type = SpacerType.VERTICAL,
                    padding = dimensionResource(id = R.dimen.spacer_medium)
                )
                /**
                 * Calendar
                 */
                Calendar(
                    modifier = Modifier.fillMaxWidth(),
                    calendarYear = calendarYear,
                    from = from,
                    to = to,
                    breakDays = state.breakDays ?: 0
                )

                InfoCalendar()
            }
        }
    }
}
