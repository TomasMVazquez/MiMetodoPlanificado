package com.applications.toms.mimetodoplanificado.ui.screen.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.domain.MethodAndStartDate
import com.applications.toms.domain.MethodChosen
import com.applications.toms.domain.enums.Method
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.alarmandnotification.alarm.createRepeatingAlarm
import com.applications.toms.mimetodoplanificado.alarmandnotification.notification.createCycleNotifications
import com.applications.toms.mimetodoplanificado.alarmandnotification.notification.createRepeatingNotification
import com.applications.toms.mimetodoplanificado.ui.components.MyLoadingContent
import com.applications.toms.mimetodoplanificado.ui.components.SnackBarType
import com.applications.toms.mimetodoplanificado.ui.components.generics.ButtonType
import com.applications.toms.mimetodoplanificado.ui.components.generics.GenericButton
import com.applications.toms.mimetodoplanificado.ui.components.generics.GenericSpacer
import com.applications.toms.mimetodoplanificado.ui.components.generics.SpacerType
import com.applications.toms.mimetodoplanificado.ui.components.settings.AlarmSettingsItem
import com.applications.toms.mimetodoplanificado.ui.components.settings.DatePickerSettingsItem
import com.applications.toms.mimetodoplanificado.ui.components.settings.InfoSettings21Cycle
import com.applications.toms.mimetodoplanificado.ui.components.settings.InfoSettingsMonthly
import com.applications.toms.mimetodoplanificado.ui.components.settings.InfoSettingsMyCycle
import com.applications.toms.mimetodoplanificado.ui.components.settings.InfoSettingsPills
import com.applications.toms.mimetodoplanificado.ui.components.settings.NotificationSettingsItem
import com.applications.toms.mimetodoplanificado.ui.screen.settings.SettingsViewModel.Event
import com.applications.toms.mimetodoplanificado.ui.screen.settings.SettingsViewModel.SettingsState
import com.applications.toms.mimetodoplanificado.ui.utils.methods.CYCLE_21_DAYS
import com.applications.toms.mimetodoplanificado.ui.utils.methods.TOTAL_CYCLE_DAYS
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.flow.collect

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun Settings(
    method: MethodAndStartDate,
    viewModel: SettingsViewModel = hiltViewModel(),
    onCancel: (SnackBarType?) -> Unit,
    onDone: (Method?) -> Unit
) {
    val state by viewModel.state.collectAsState(SettingsState())
    val context = LocalContext.current

    viewModel.setMethodChosen(method)

    LaunchedEffect(key1 = viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is Event.Continue -> {
                    event.saveMethodState
                        .onSuccess {
                            viewModel.resetState()
                            if (event.method == Method.CYCLE) {
                                createCycleNotifications(
                                    context = context,
                                    daysFromStart = event.daysFromStart + 1
                                )
                            }
                            if (event.notificationsState == true && event.method != null) {
                                createRepeatingNotification(
                                    context = context,
                                    timeInMillis = event.notificationTimeInMillis,
                                    method = event.method,
                                    totalDaysCycle = event.totalDaysCycle,
                                    daysFromStart = event.daysFromStart
                                )
                            }
                            if (event.alarmState == true && event.method != null) {
                                createRepeatingAlarm(
                                    context = context,
                                    timeInMillis = event.alarmTimeInMillis,
                                    method = event.method,
                                    totalDaysCycle = event.totalDaysCycle,
                                    daysFromStart = event.daysFromStart
                                )
                            }
                            onDone(state.methodAndStartDate.methodChosen)
                        }
                        .onFailure {
                            viewModel.resetState()
                            onCancel(SnackBarType.ERROR)
                        }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_small))
    ) {

        /**
         * Cancel Button
         */
        IconButton(
            onClick = {
                viewModel.resetState()
                onCancel(null)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.content_description_close),
                tint = MaterialTheme.colors.onBackground
            )
        }

        Column(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.padding_large),
                top = dimensionResource(id = R.dimen.padding_xlarge),
                end = dimensionResource(id = R.dimen.padding_large)
            ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            /**
             * Title
             */
            Text(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                text = stringResource(
                    R.string.settings_title,
                    when (method.methodChosen) {
                        Method.PILLS -> stringResource(R.string.pills)
                        Method.RING -> stringResource(R.string.ring)
                        Method.SHOOT -> stringResource(R.string.injection)
                        Method.PATCH -> stringResource(R.string.patch)
                        Method.CYCLE -> stringResource(R.string.cycle)
                        else -> ""
                    }
                ),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onPrimary
            )

            LazyColumn {
                /**
                 * Start Date Picker
                 */
                item {
                    DatePickerSettingsItem(
                        date = state.methodAndStartDate.startDate,
                        cycle = state.methodAndStartDate.methodChosen == Method.CYCLE
                    ) {
                        viewModel.changeStartDate(it)
                    }

                    GenericSpacer(
                        type = SpacerType.VERTICAL,
                        padding = dimensionResource(id = R.dimen.spacer_xsmall)
                    )
                }

                /**
                 * Info about Dates bases on method
                 */
                item {
                    when (method.methodChosen) {
                        Method.PILLS -> {
                            viewModel.changeEnable(true)
                            InfoSettingsPills(
                                startDate = state.methodAndStartDate.startDate,
                                pillsBreakDays = state.breakDays
                            ) {
                                viewModel.changeBreakDays(it)
                            }
                        }
                        Method.RING -> {
                            InfoSettings21Cycle(startDate = state.methodAndStartDate.startDate)
                            viewModel.changeBreakDays(TOTAL_CYCLE_DAYS.toInt().minus(CYCLE_21_DAYS.toInt()))
                            viewModel.changeEnable(true)
                        }
                        Method.SHOOT -> {
                            viewModel.changeBreakDays(0)
                            InfoSettingsMonthly(startDate = state.methodAndStartDate.startDate) {
                                viewModel.changeTotalDaysCycle(it)
                                viewModel.changeEnable(true)
                            }
                        }
                        Method.PATCH -> {
                            InfoSettings21Cycle(startDate = state.methodAndStartDate.startDate)
                            viewModel.changeBreakDays(TOTAL_CYCLE_DAYS.toInt().minus(CYCLE_21_DAYS.toInt()))
                            viewModel.changeEnable(true)
                        }
                        Method.CYCLE -> {
                            viewModel.changeBreakDays(0)
                            viewModel.changeEnable(true)
                            InfoSettingsMyCycle(startDate = state.methodAndStartDate.startDate)
                        }
                    }

                    GenericSpacer(
                        type = SpacerType.VERTICAL,
                        padding = dimensionResource(id = R.dimen.padding_medium)
                    )
                }

                if (state.methodAndStartDate.methodChosen != Method.CYCLE) {
                    /**
                     * Notification & Alarm Time Picker
                     */
                    item {
                        NotificationSettingsItem(
                            isEnable = state.isNotificationEnable,
                            timeSet = state.notificationTime
                        ) { isNotifEnable, time ->
                            viewModel.changeNotificationValue(
                                value = isNotifEnable,
                                time = time
                            )
                        }

                        AlarmSettingsItem(
                            isEnable = state.isAlarmEnable,
                            timeSet = state.alarmTime
                        ) { isAlarmEnabled, time ->
                            viewModel.changeAlarmValue(
                                value = isAlarmEnabled,
                                time = time
                            )
                        }

                        GenericSpacer(
                            type = SpacerType.VERTICAL,
                            padding = dimensionResource(id = R.dimen.padding_xlarge)
                        )
                    }
                }
                /**
                 * Confirm Button
                 */
                item {
                    AnimatedVisibility(visible = !state.loading) {
                        GenericButton(
                            modifier = Modifier.fillMaxWidth(),
                            buttonType = ButtonType.HIGH_EMPHASIS,
                            text = stringResource(R.string.settings_btn_start),
                            enable = state.enable
                        ) {
                            viewModel.changeLoading(!state.loading)
                            with(state) {
                                viewModel.onSaveMethodChosen(
                                    MethodChosen(
                                        methodAndStartDate = methodAndStartDate,
                                        totalDaysCycle = totalDaysCycle,
                                        breakDays = breakDays,
                                        isNotificationEnable = isNotificationEnable,
                                        notificationTime = notificationTime ?: "",
                                        isAlarmEnable = isAlarmEnable,
                                        alarmTime = alarmTime ?: ""
                                    )
                                )
                            }
                        }
                    }

                    AnimatedVisibility(visible = state.loading) {
                        MyLoadingContent(Modifier.fillMaxWidth(), Alignment.BottomCenter)
                    }
                }
            }
        }
    }
}

