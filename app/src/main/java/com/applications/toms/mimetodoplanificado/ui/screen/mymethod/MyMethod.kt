package com.applications.toms.mimetodoplanificado.ui.screen.mymethod

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.applications.toms.domain.MethodChosen
import com.applications.toms.domain.enums.Method
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.alarmandnotification.alarm.cancelRepeatingAlarm
import com.applications.toms.mimetodoplanificado.alarmandnotification.alarm.createRepeatingAlarm
import com.applications.toms.mimetodoplanificado.alarmandnotification.notification.cancelRepeatingNotification
import com.applications.toms.mimetodoplanificado.alarmandnotification.notification.createRepeatingNotification
import com.applications.toms.mimetodoplanificado.ui.components.CircularDaysProgress
import com.applications.toms.mimetodoplanificado.ui.components.DefaultSnackbar
import com.applications.toms.mimetodoplanificado.ui.components.InfoNotificationsAndAlarm
import com.applications.toms.mimetodoplanificado.ui.components.MyMethodCustomToolbar
import com.applications.toms.mimetodoplanificado.ui.components.SnackBarType
import com.applications.toms.mimetodoplanificado.ui.components.customcalendar.Calendar
import com.applications.toms.mimetodoplanificado.ui.components.customcalendar.InfoCalendar
import com.applications.toms.mimetodoplanificado.ui.components.dialogs.AlertDialogConfirmMethodChange
import com.applications.toms.mimetodoplanificado.ui.components.dialogs.AlertDialogSuccess
import com.applications.toms.mimetodoplanificado.ui.components.generics.GenericSpacer
import com.applications.toms.mimetodoplanificado.ui.components.generics.SpacerType
import com.applications.toms.mimetodoplanificado.ui.screen.mymethod.MyMethodViewModel.State
import com.applications.toms.mimetodoplanificado.ui.utils.convertToTimeInMills
import com.applications.toms.mimetodoplanificado.ui.utils.hasBeenReboot
import com.applications.toms.mimetodoplanificado.ui.utils.onRebooted
import com.applications.toms.mimetodoplanificado.ui.utils.safeLet
import com.applications.toms.mimetodoplanificado.ui.utils.toCalendarMonth
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun MyMethod(
    viewModel: MyMethodViewModel = hiltViewModel(),
    onMethodDeleted: (Boolean, Boolean) -> Unit,
    goToAlarmSettings: () -> Unit
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    val state by viewModel.state.collectAsState(State())
    var openDialog by remember { mutableStateOf(false) }

    val channel = remember { Channel<Int>(Channel.CONFLATED) }
    var snackBarType by remember { mutableStateOf(SnackBarType.DEFAULT) }

    LaunchedEffect(key1 = viewModel.event) {
        viewModel.event.collect {
            when (it) {
                MyMethodViewModel.Event.ConfirmMethodChange -> {
                    openDialog = true
                }
                MyMethodViewModel.Event.MethodDeleted -> {
                    openDialog = false
                    onMethodDeleted(state.isNotificationEnable ?: false, state.isAlarmEnable ?: false)
                }
                MyMethodViewModel.Event.GoToAlarmSettings -> {
                    goToAlarmSettings()
                }
                is MyMethodViewModel.Event.SnackBarEvent -> {
                    snackBarType = it.snackBarType
                    channel.trySend(it.snackBarType.channel)
                }
            }
        }
    }

    LaunchedEffect(channel) {
        channel.receiveAsFlow().collect {
            scaffoldState.snackbarHostState.showSnackbar(
                message = when(it){
                    SnackBarType.ERROR.channel -> context.getString(R.string.snackbar_message_error_message)
                    else -> context.getString(R.string.snackbar_message_generic)
                }
            )
        }
    }

    if (openDialog)
        AlertDialogConfirmMethodChange(
            onCancel = { openDialog = false },
            onConfirm = { viewModel.onDeleteCurrentMethod() }
        )

    Scaffold(scaffoldState = scaffoldState, snackbarHost = { scaffoldState.snackbarHostState }) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (!state.loading) {
                Column() {
                    MyMethodCustomToolbar(
                        onChangeMethodClick = { viewModel.onMethodChangeClick() },
                        onGoToAlarmSettingsClick = { viewModel.onGoToAlarmSettingsClick() }
                    )

                    MyMethodContent(state)

                    state.methodChosen?.let { ConfirmRebootSettings(hasBeenReboot(context), context, it) }

                }
            }

            DefaultSnackbar(snackbarHostState = scaffoldState.snackbarHostState, onDismiss = {
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
            },modifier = Modifier.align(Alignment.BottomCenter), snackBarType = snackBarType)
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
                text = when (state.methodChosen?.methodAndStartDate?.methodChosen) {
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
            InfoNotificationsAndAlarm(state.isAlarmEnable, state.alarmTime, state.isNotificationEnable, state.notificationTime)

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

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun ConfirmRebootSettings(open: Boolean, context: Context, methodChosen: MethodChosen) {
    onRebooted(context, false)
    var openConfirmDialog by remember { mutableStateOf(open) }

    AlertDialogSuccess(
        show = openConfirmDialog,
        successTitle = stringResource(id = R.string.success_dialog_title),
        successDescription = stringResource(id = R.string.success_reboot_dialog_desc),
    ) {
        methodChosen.methodAndStartDate.methodChosen?.let {
            val daysCycle = methodChosen.totalDaysCycle.toInt()
            val fromStart = methodChosen.methodAndStartDate.startDate.until(
                LocalDate.now(),
                ChronoUnit.DAYS
            )

            if (methodChosen.isNotificationEnable)
                methodChosen.notificationTime?.let { notificationTime ->
                    createRepeatingNotification(
                        context = context,
                        timeInMillis = notificationTime.convertToTimeInMills(),
                        method = it,
                        totalDaysCycle = daysCycle,
                        daysFromStart = fromStart
                    )
                }
            else
                cancelRepeatingNotification(context)

            if (methodChosen.isAlarmEnable)
                methodChosen.alarmTime?.let { alarmTime ->
                    createRepeatingAlarm(
                        context = context,
                        timeInMillis = alarmTime.convertToTimeInMills(),
                        method = it,
                        totalDaysCycle = daysCycle,
                        daysFromStart = fromStart
                    )
                }
            else
                cancelRepeatingAlarm(context)
        }
        openConfirmDialog = false
    }
}
