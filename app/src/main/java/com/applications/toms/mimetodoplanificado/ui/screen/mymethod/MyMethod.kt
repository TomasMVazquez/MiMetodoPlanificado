package com.applications.toms.mimetodoplanificado.ui.screen.mymethod

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.applications.toms.domain.enums.Method
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.AlertDialogConfirmMethodChange
import com.applications.toms.mimetodoplanificado.ui.components.CircularDaysProgress
import com.applications.toms.mimetodoplanificado.ui.components.InfoNotificationsAndAlarm
import com.applications.toms.mimetodoplanificado.ui.components.MyMethodCustomToolbar
import com.applications.toms.mimetodoplanificado.ui.components.customcalendar.Calendar
import com.applications.toms.mimetodoplanificado.ui.components.customcalendar.InfoCalendar
import com.applications.toms.mimetodoplanificado.ui.components.generics.GenericSpacer
import com.applications.toms.mimetodoplanificado.ui.components.generics.SpacerType
import com.applications.toms.mimetodoplanificado.ui.screen.mymethod.MyMethodViewModel.State
import com.applications.toms.mimetodoplanificado.ui.utils.safeLet
import com.applications.toms.mimetodoplanificado.ui.utils.toCalendarMonth
import kotlinx.coroutines.flow.collect
import java.time.LocalDate

@Composable
fun MyMethod(viewModel: MyMethodViewModel = hiltViewModel(), onMethodDeleted: () -> Unit) {

    val state by viewModel.state.collectAsState(State())
    var openDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = viewModel.event) {
        viewModel.event.collect {
            when(it) {
                is MyMethodViewModel.Event.ConfirmMethodChange -> {
                    openDialog = true
                }
                MyMethodViewModel.Event.MethodDeleted -> {
                    openDialog = false
                    onMethodDeleted()
                }
            }
        }
    }

    if (openDialog)
        AlertDialogConfirmMethodChange(
            onCancel = { openDialog = false },
            onConfirm = { viewModel.onDeleteCurrentMethod() }
        )

    Box(modifier = Modifier.fillMaxSize()) {
        if (!state.loading)
            Column() {
                MyMethodCustomToolbar(
                    onChangeMethodClick = { viewModel.onMethodChangeClick() },
                    onGoToSettingsClick = { viewModel.onGoToSettingsClick() }
                )

                MyMethodContent(state)
            }
    }

}

@Composable
fun MyMethodContent(state: State) {
    safeLet(state.startDate, state.endDate) { from, to ->
        val monthFrom = from.toCalendarMonth()
        val monthTo = to.toCalendarMonth()
        val calendarYear = if (monthFrom.monthNumber == monthTo.monthNumber)
            listOf(monthFrom) else listOf(monthFrom, monthTo)

        val totalDays = (from.until(to).days + 1).toFloat()
        val currentDay = (from.until(LocalDate.now()).days + 1).toFloat()
        val breakDayStarts = state.breakDays?.let { to.minusDays(it.toLong() - 1) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = dimensionResource(id = R.dimen.padding_medium),
                    top = dimensionResource(id = R.dimen.no_padding),
                    end = dimensionResource(id = R.dimen.padding_medium),
                    bottom = dimensionResource(id = R.dimen.padding_medium)
                )
        ) {
            /**
             * Title
             */
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(id = R.dimen.padding_medium)),
                text = when (state.methodChosen) {
                    Method.PILLS -> stringResource(R.string.pills)
                    Method.RING -> stringResource(R.string.ring)
                    Method.SHOOT -> stringResource(R.string.injection)
                    Method.PATCH -> stringResource(R.string.patch)
                    null -> ""
                },
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center
            )

            GenericSpacer(
                type = SpacerType.VERTICAL,
                padding = dimensionResource(id = R.dimen.spacer_medium)
            )
            /**
             * Progress Day
             */
            CircularDaysProgress(
                modifier = Modifier.weight(1f),
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
            InfoNotificationsAndAlarm(state.alarm, state.alarmTime, state.notifications, state.notificationTime)

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
