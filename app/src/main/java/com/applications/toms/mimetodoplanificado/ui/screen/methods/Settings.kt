package com.applications.toms.mimetodoplanificado.ui.screen.methods

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.applications.toms.domain.UserAction
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.ClickableOutlineTextField
import com.applications.toms.mimetodoplanificado.ui.components.CustomCalendarView
import com.applications.toms.mimetodoplanificado.ui.components.InfoSettingsPills
import com.applications.toms.mimetodoplanificado.ui.components.InfoSettingsRing
import com.applications.toms.mimetodoplanificado.ui.components.generics.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun Settings(methodChosen: UserAction, onDone: () -> Unit) {
    var onStartClick by rememberSaveable { mutableStateOf(false) }
    var startDate by rememberSaveable { mutableStateOf(LocalDate.now()) }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var pillsBreakDays by rememberSaveable { mutableStateOf(5) }
    var notifictions by rememberSaveable { mutableStateOf(true) }
    var alarm by rememberSaveable { mutableStateOf(true) }

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

                    ClickableOutlineTextField(input = startDate.format(DateTimeFormatter.ofPattern("dd/MM/yy"))) {
                        showDatePicker = !showDatePicker
                    }

                    AnimatedVisibility(visible = showDatePicker) {
                        CustomCalendarView {
                            startDate = it
                            showDatePicker = !showDatePicker
                        }
                    }

                    GenericSpacer(
                        type = SpacerType.VERTICAL,
                        padding = dimensionResource(id = R.dimen.spacer_xsmall)
                    )
                }

                item {
                    when(methodChosen) {
                        UserAction.PILLS -> {
                            InfoSettingsPills(
                                startDate = startDate,
                                pillsBreakDays = pillsBreakDays
                            ) {
                                pillsBreakDays = it
                            }
                        }
                        UserAction.RING -> {
                            InfoSettingsRing(startDate = startDate)
                        }
                        UserAction.SHOOT -> TODO()
                        UserAction.PATCH -> TODO()
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
                        notifictions
                    ){
                        notifictions = it
                    }

                    GenericSwitchSetting(
                        stringResource(R.string.settings_alarm_title),
                        stringResource(R.string.settings_alarm_info),
                        alarm
                    ){
                        alarm = it
                    }

                    GenericSpacer(
                        type = SpacerType.VERTICAL,
                        padding = dimensionResource(id = R.dimen.padding_small)
                    )
                }

                item {
                    AnimatedVisibility(visible = !onStartClick) {
                        GenericButton(
                            modifier = Modifier.fillMaxWidth(),
                            buttonType = ButtonType.HIGH_EMPHASIS,
                            text = "COMENZAR"
                        ) {
                            onStartClick = !onStartClick
                        }
                    }

                    AnimatedVisibility(visible = onStartClick) {
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

