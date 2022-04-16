package com.applications.toms.mimetodoplanificado.ui.screen.alarmsettings

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.alarm.cancelRepeatingAlarm
import com.applications.toms.mimetodoplanificado.alarm.createRepeatingAlarm
import com.applications.toms.mimetodoplanificado.notification.cancelRepeatingNotification
import com.applications.toms.mimetodoplanificado.notification.createRepeatingNotification
import com.applications.toms.mimetodoplanificado.ui.components.dialogs.AlertDialogError
import com.applications.toms.mimetodoplanificado.ui.components.dialogs.AlertDialogOkCancel
import com.applications.toms.mimetodoplanificado.ui.components.dialogs.AlertDialogSuccess
import com.applications.toms.mimetodoplanificado.ui.components.generics.ButtonType
import com.applications.toms.mimetodoplanificado.ui.components.generics.GenericButton
import com.applications.toms.mimetodoplanificado.ui.components.generics.GenericSpacer
import com.applications.toms.mimetodoplanificado.ui.components.generics.SpacerType
import com.applications.toms.mimetodoplanificado.ui.components.settings.AlarmSettingsItem
import com.applications.toms.mimetodoplanificado.ui.components.settings.NotificationSettingsItem
import com.applications.toms.mimetodoplanificado.ui.theme.Warning
import com.applications.toms.mimetodoplanificado.ui.utils.convertToTimeInMills
import com.google.accompanist.pager.ExperimentalPagerApi
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun AlarmSettings(
    alarmSettingsViewModel: AlarmSettingsViewModel = hiltViewModel(),
    onSave: () -> Unit,
    goBack: () -> Unit
) {
    val context = LocalContext.current
    val state by alarmSettingsViewModel.state.collectAsState(AlarmSettingsViewModel.AlarmSettingsState())
    var openWarningDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        AlertDialogError(
            show = state.error,
            errorTitle = stringResource(id = R.string.error_dialog_title),
            errorDescription = stringResource(id = R.string.error_dialog_desc)
        ) {
            alarmSettingsViewModel.onDismissError()
        }

        AlertDialogSuccess(
            show = state.changesSaved,
            successTitle = stringResource(id = R.string.success_dialog_title),
            successDescription = stringResource(id = R.string.success_dialog_desc),
        ) {
            alarmSettingsViewModel.methodChosen.methodAndStartDate.methodChosen?.let {
                val daysCycle = alarmSettingsViewModel.methodChosen.totalDaysCycle.toInt()
                val fromStart = alarmSettingsViewModel.methodChosen.methodAndStartDate.startDate.until(
                    LocalDate.now(),
                    ChronoUnit.DAYS
                )
                if (state.hasNotificationChange) {
                    if (state.isNotificationEnable)
                        createRepeatingNotification(
                            context = context,
                            timeInMillis = state.notificationTime.convertToTimeInMills(),
                            method = it,
                            totalDaysCycle = daysCycle,
                            daysFromStart = fromStart
                        )
                    else
                        cancelRepeatingNotification(context)
                }
                if (state.hasAlarmChange) {
                    if (state.isAlarmEnable)
                        createRepeatingAlarm(
                            context = context,
                            timeInMillis = state.alarmTime.convertToTimeInMills(),
                            method = it,
                            totalDaysCycle = daysCycle,
                            daysFromStart = fromStart
                        )
                    else
                        cancelRepeatingAlarm(context)
                }
            }
            onSave()
        }

        AlertDialogOkCancel(
            show = openWarningDialog,
            imgVector = Icons.Outlined.Warning,
            color = Warning,
            title = stringResource(id = R.string.ok_cancel_dialog_title),
            subtitle = stringResource(id = R.string.ok_cancel_dialog_warning),
            description = stringResource(id = R.string.ok_cancel_dialog_desc),
            onCancel = {
                openWarningDialog = false
            },
            onConfirm = {
                goBack()
            }
        )

        if (state.dataRetrieved) {
            AlarmSettingsContent(
                notificationsState = state.isNotificationEnable,
                notificationTime = state.notificationTime,
                alarmState = state.isAlarmEnable,
                alarmTime = state.alarmTime,
                onUpdateNotif = { enable, time ->
                    alarmSettingsViewModel.changeNotificationValue(enable = enable, time = time)
                },
                onUpdateAlarm = { enable, time ->
                    alarmSettingsViewModel.changeAlarmValue(enable = enable, time = time)
                },
                onSaveChanges = {
                    alarmSettingsViewModel.onSaveMethodChosen(
                        notificationsState = state.isNotificationEnable,
                        notificationTime = state.notificationTime,
                        alarmState = state.isAlarmEnable,
                        alarmTime = state.alarmTime
                    )
                },
                goBack = {
                    if (state.changeStateNotSaved)
                        openWarningDialog = state.changeStateNotSaved
                    else
                        goBack()
                }
            )
        }
    }
}

@Composable
fun AlarmSettingsContent(
    notificationsState: Boolean,
    notificationTime: String?,
    alarmState: Boolean,
    alarmTime: String?,
    onUpdateNotif: (Boolean, String) -> Unit,
    onUpdateAlarm: (Boolean, String) -> Unit,
    onSaveChanges: () -> Unit,
    goBack: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_tiny))) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = goBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.content_description_back),
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }

        LazyColumn(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_large))) {
            item {
                NotificationSettingsItem(
                    isEnable = notificationsState,
                    timeSet = notificationTime
                ) { isNotifEnable, time ->
                    onUpdateNotif(isNotifEnable, time)
                }
            }

            item {
                AlarmSettingsItem(
                    isEnable = alarmState,
                    timeSet = alarmTime
                ) { isAlarmEnabled, time ->
                    onUpdateAlarm(isAlarmEnabled, time)
                }
            }

            item {
                GenericSpacer(
                    type = SpacerType.VERTICAL,
                    padding = dimensionResource(id = R.dimen.padding_xlarge)
                )

                GenericButton(
                    modifier = Modifier.fillMaxWidth(),
                    buttonType = ButtonType.HIGH_EMPHASIS,
                    text = stringResource(R.string.settings_btn_save)
                ) {
                    onSaveChanges()
                }
            }

        }
    }
}