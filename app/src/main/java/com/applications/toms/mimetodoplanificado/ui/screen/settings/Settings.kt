package com.applications.toms.mimetodoplanificado.ui.screen.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.domain.MethodAndStartDate
import com.applications.toms.domain.enums.Method
import com.applications.toms.domain.MethodChosen
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.MyLoadingContent
import com.applications.toms.mimetodoplanificado.ui.components.settings.InfoSettingsPills
import com.applications.toms.mimetodoplanificado.ui.components.settings.InfoSettingsRing
import com.applications.toms.mimetodoplanificado.ui.components.generics.*
import com.applications.toms.mimetodoplanificado.ui.components.settings.AlarmSettingsItem
import com.applications.toms.mimetodoplanificado.ui.components.settings.DatePickerSettingsItem
import com.applications.toms.mimetodoplanificado.ui.components.settings.NotificationSettingsItem
import com.applications.toms.mimetodoplanificado.ui.screen.settings.SettingsViewModel.*
import kotlinx.coroutines.flow.collect

@Composable
fun Settings(
    method: MethodAndStartDate,
    viewModel: SettingsViewModel = hiltViewModel(),
    onCancel: () -> Unit,
    onDone: () -> Unit
) {

    LaunchedEffect(key1 = viewModel.event) {
        viewModel.event.collect {
            when(it) {
                is Event.Continue -> {
                    it.state
                        .onSuccess {
                            onDone()
                        }
                        .onFailure {
                            it
                            /**
                             * TODO MANAGE ERROR STATES
                             */
                        }
                }
            }
        }
    }

    val state by viewModel.state.collectAsState(SettingsState())
    viewModel.setMethodChosen(method)

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(dimensionResource(id = R.dimen.padding_xsmall))
    ) {

        /**
         * Cancel Button
         */
        IconButton(onClick = onCancel) {
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
                        null -> ""
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
                    DatePickerSettingsItem(state.methodAndStartDate.startDate) {
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
                            InfoSettingsPills(
                                startDate = state.methodAndStartDate.startDate,
                                pillsBreakDays = state.pillsBreakDays
                            ) {
                                viewModel.changePillsBreakDays(it)
                            }
                        }
                        Method.RING -> {
                            InfoSettingsRing(startDate = state.methodAndStartDate.startDate)
                        }
                        Method.SHOOT -> "" // TODO ADD INFO
                        Method.PATCH -> "" // TODO ADD INFO
                        else -> onCancel()
                    }

                    GenericSpacer(
                        type = SpacerType.VERTICAL,
                        padding = dimensionResource(id = R.dimen.padding_small)
                    )
                }

                /**
                 * Notification & Alarm Time Picker
                 */
                item {
                    NotificationSettingsItem { isNotifEnable, time ->
                        viewModel.changeNotificationValue(
                            value = isNotifEnable,
                            time = time
                        )
                    }

                    AlarmSettingsItem { isAlarmEnabled, time ->
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

                /**
                 * Confirm Button
                 */
                item {
                    AnimatedVisibility(visible = !state.loading) {
                        GenericButton(
                            modifier = Modifier.fillMaxWidth(),
                            buttonType = ButtonType.HIGH_EMPHASIS,
                            text = stringResource(R.string.settings_btn_start)
                        ) {
                            viewModel.changeLoading(!state.loading)
                            with(state) {
                                viewModel.onSaveMethodChosen(
                                    MethodChosen(
                                        methodAndStartDate = methodAndStartDate,
                                        pillsBreakDays = pillsBreakDays,
                                        notifications = notifications,
                                        notificationTime = notificationTime,
                                        alarm = alarm,
                                        alarmTime = alarmTime
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

