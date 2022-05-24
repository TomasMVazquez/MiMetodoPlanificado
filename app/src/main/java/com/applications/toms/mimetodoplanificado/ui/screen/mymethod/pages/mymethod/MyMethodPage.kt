package com.applications.toms.mimetodoplanificado.ui.screen.mymethod.pages.mymethod

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.applications.toms.domain.enums.Method
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.CircularDaysProgress
import com.applications.toms.mimetodoplanificado.ui.components.InfoNotificationsAndAlarm
import com.applications.toms.mimetodoplanificado.ui.components.customcalendar.Calendar
import com.applications.toms.mimetodoplanificado.ui.components.customcalendar.InfoCalendar
import com.applications.toms.mimetodoplanificado.ui.components.generics.GenericSpacer
import com.applications.toms.mimetodoplanificado.ui.components.generics.SpacerType
import com.applications.toms.mimetodoplanificado.ui.screen.mymethod.MyMethodViewModel.MyMethodState
import com.applications.toms.mimetodoplanificado.ui.utils.safeLet
import com.applications.toms.mimetodoplanificado.ui.utils.toCalendarMonth
import java.time.LocalDate


@Composable
fun MyMethodPage(state: MyMethodState) {
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
                .padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
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
                    Method.CYCLE -> TODO()
                    null -> ""
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