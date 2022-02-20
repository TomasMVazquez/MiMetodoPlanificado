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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.applications.toms.domain.Method
import com.applications.toms.domain.UserAction
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.ClickableOutlineTextField
import com.applications.toms.mimetodoplanificado.ui.components.CustomCalendarView
import com.applications.toms.mimetodoplanificado.ui.components.InfoSettingsPills
import com.applications.toms.mimetodoplanificado.ui.components.InfoSettingsRing
import com.applications.toms.mimetodoplanificado.ui.components.generics.*
import java.time.format.DateTimeFormatter

@Composable
fun Settings(
    methodChosen: Method?,
    viewModel: SettingsViewModel = viewModel(),
    onDone: () -> Unit
) {

    methodChosen?.let { viewModel.setMethodChosen(it) }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(dimensionResource(id = R.dimen.padding_xsmall))) {
        IconButton(onClick = onDone) {
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

            Text(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                text = stringResource(R.string.settings_title),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onPrimary
            )

            LazyColumn {
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(id = R.dimen.padding_small)),
                        text = stringResource(R.string.settings_text_start_date),
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.onPrimary
                    )

                    ClickableOutlineTextField(input = viewModel.state.startDate.format(DateTimeFormatter.ofPattern("dd/MM/yy"))) {
                        viewModel.changeShowDatePicker(!viewModel.state.showDatePicker)
                    }

                    AnimatedVisibility(visible = viewModel.state.showDatePicker) {
                        CustomCalendarView {
                            viewModel.changeStartDate(it)
                            viewModel.changeShowDatePicker(!viewModel.state.showDatePicker)
                        }
                    }

                    GenericSpacer(
                        type = SpacerType.VERTICAL,
                        padding = dimensionResource(id = R.dimen.spacer_xsmall)
                    )
                }

                item {
                    when (methodChosen) {
                        Method.PILLS -> {
                            InfoSettingsPills(
                                startDate = viewModel.state.startDate,
                                pillsBreakDays = viewModel.state.pillsBreakDays
                            ) {
                                viewModel.changePillsBreakDays(it)
                            }
                        }
                        Method.RING -> {
                            InfoSettingsRing(startDate = viewModel.state.startDate)
                        }
                        Method.SHOOT -> TODO()
                        Method.PATCH -> TODO()
                    }

                    GenericSpacer(
                        type = SpacerType.VERTICAL,
                        padding = dimensionResource(id = R.dimen.padding_small)
                    )
                }

                item {
                    GenericSwitchSetting(
                        stringResource(R.string.settings_notifications_title),
                        stringResource(R.string.settings_notification_info),
                        viewModel.state.notifications
                    ){
                        viewModel.changeNotificationValue(!viewModel.state.notifications)
                    }

                    GenericSwitchSetting(
                        stringResource(R.string.settings_alarm_title),
                        stringResource(R.string.settings_alarm_info),
                        viewModel.state.alarm
                    ){
                        viewModel.changeAlarmValue(!viewModel.state.alarm)
                    }

                    GenericSpacer(
                        type = SpacerType.VERTICAL,
                        padding = dimensionResource(id = R.dimen.padding_small)
                    )
                }

                item {
                    AnimatedVisibility(visible = !viewModel.state.loading) {
                        GenericButton(
                            modifier = Modifier.fillMaxWidth(),
                            buttonType = ButtonType.HIGH_EMPHASIS,
                            text = stringResource(R.string.settings_btn_start)
                        ) {
                            viewModel.changeLoading(!viewModel.state.loading)
                        }
                    }

                    AnimatedVisibility(visible = viewModel.state.loading) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.BottomCenter
                        ){
                            CircularProgressIndicator(
                                color = MaterialTheme.colors.secondary
                            )
                        }
                    }

                }
            }

        }
    }
}
