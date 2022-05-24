package com.applications.toms.mimetodoplanificado.ui.screen.mymethod

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.applications.toms.domain.MethodChosen
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.alarmandnotification.alarm.cancelRepeatingAlarm
import com.applications.toms.mimetodoplanificado.alarmandnotification.alarm.createRepeatingAlarm
import com.applications.toms.mimetodoplanificado.alarmandnotification.notification.cancelRepeatingNotification
import com.applications.toms.mimetodoplanificado.alarmandnotification.notification.createRepeatingNotification
import com.applications.toms.mimetodoplanificado.ui.components.DefaultSnackbar
import com.applications.toms.mimetodoplanificado.ui.components.MyMethodCustomToolbar
import com.applications.toms.mimetodoplanificado.ui.components.SnackBarType
import com.applications.toms.mimetodoplanificado.ui.components.dialogs.AlertDialogConfirmMethodChange
import com.applications.toms.mimetodoplanificado.ui.components.dialogs.AlertDialogSuccess
import com.applications.toms.mimetodoplanificado.ui.screen.mymethod.MyMethodViewModel.State
import com.applications.toms.mimetodoplanificado.ui.screen.mymethod.pages.mycycle.MyCyclePage
import com.applications.toms.mimetodoplanificado.ui.screen.mymethod.pages.mymethod.MyMethodPage
import com.applications.toms.mimetodoplanificado.ui.utils.convertToTimeInMills
import com.applications.toms.mimetodoplanificado.ui.utils.hasBeenReboot
import com.applications.toms.mimetodoplanificado.ui.utils.onRebooted
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MyMethodScreen(
    appState: MyMethodState = rememberMyMethodState(),
    viewModel: MyMethodViewModel = hiltViewModel(),
    onMethodDeleted: (Boolean, Boolean) -> Unit,
    goToAlarmSettings: () -> Unit
) {
    val state by viewModel.state.collectAsState(State())

    LaunchedEffect(key1 = viewModel.event) {
        viewModel.event.collect {
            when (it) {
                MyMethodViewModel.Event.ConfirmMethodChange -> {
                    appState.changeOpenDialogState(true)
                }
                MyMethodViewModel.Event.MethodDeleted -> {
                    appState.changeOpenDialogState(false)
                    onMethodDeleted(
                        state.myMethodState.isNotificationEnable ?: false,
                        state.myMethodState.isAlarmEnable ?: false
                    )
                }
                MyMethodViewModel.Event.GoToAlarmSettings -> {
                    goToAlarmSettings()
                }
                is MyMethodViewModel.Event.SnackBarEvent -> {
                    appState.addSnackBarType(it.snackBarType)
                    appState.channel.trySend(it.snackBarType.channel)
                }
            }
        }
    }

    LaunchedEffect(appState.channel) {
        appState.channel.receiveAsFlow().collect {
            appState.scaffoldState.snackbarHostState.showSnackbar(
                message = when (it) {
                    SnackBarType.ERROR.channel -> appState.context.getString(R.string.snackbar_message_error_message)
                    else -> appState.context.getString(R.string.snackbar_message_generic)
                }
            )
        }
    }

    if (appState.state.collectAsState().value.openDialog)
        AlertDialogConfirmMethodChange(
            onCancel = { appState.changeOpenDialogState(false) },
            onConfirm = { viewModel.onDeleteCurrentMethod() }
        )

    Scaffold(
        scaffoldState = appState.scaffoldState,
        snackbarHost = { appState.scaffoldState.snackbarHostState }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (!state.loading) {
                Column() {
                    MyMethodCustomToolbar(
                        onChangeMethodClick = { viewModel.onMethodChangeClick() },
                        onGoToAlarmSettingsClick = { viewModel.onGoToAlarmSettingsClick() }
                    )

                    HorizontalPagerIndicator(
                        pagerState = appState.pagerState,
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.padding_small))
                            .align(Alignment.CenterHorizontally),
                        activeColor = MaterialTheme.colors.secondary,
                        inactiveColor = MaterialTheme.colors.secondary.copy(ContentAlpha.disabled)
                    )

                    HorizontalPager(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        count = 2,
                        state = appState.pagerState
                    ) { page ->
                        if (page == 0)
                            MyMethodPage(state.myMethodState)
                        else
                            MyCyclePage(state.myCycleState)
                    }

                    state.myMethodState.methodChosen?.let {
                        ConfirmRebootSettings(
                            open = hasBeenReboot(appState.context),
                            context = appState.context,
                            methodChosen = it
                        )
                    }

                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colors.secondary)
                }
            }

            DefaultSnackbar(
                snackbarHostState = appState.scaffoldState.snackbarHostState,
                onDismiss = {
                    appState.scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                },
                modifier = Modifier.align(Alignment.BottomCenter),
                snackBarType = appState.state.collectAsState().value.snackBarType
            )
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
