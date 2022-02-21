package com.applications.toms.mimetodoplanificado.ui.components.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.CustomTimePicker
import com.applications.toms.mimetodoplanificado.ui.components.decimalFormat
import com.applications.toms.mimetodoplanificado.ui.components.generics.ButtonType
import com.applications.toms.mimetodoplanificado.ui.components.generics.GenericButton
import com.applications.toms.mimetodoplanificado.ui.components.generics.GenericSpacer
import com.applications.toms.mimetodoplanificado.ui.components.generics.GenericSwitchSetting
import com.applications.toms.mimetodoplanificado.ui.components.generics.SpacerType
import java.time.LocalDateTime

@Composable
fun AlarmSettingsItem(onTimeSelected: (Boolean, String) -> Unit) {
    var showTimePicker by rememberSaveable { mutableStateOf(false) }
    var isAlarmEnable by rememberSaveable { mutableStateOf(false) }
    var timePicked by rememberSaveable {
        mutableStateOf(
            "${decimalFormat.format(LocalDateTime.now().hour)}:00"
        )
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        GenericSwitchSetting(
            stringResource(R.string.settings_alarm_title),
            stringResource(R.string.settings_alarm_info),
            isAlarmEnable
        ){
            isAlarmEnable = it
            showTimePicker = it
        }

        GenericSpacer(
            type = SpacerType.VERTICAL,
            padding = dimensionResource(id = R.dimen.padding_xsmall)
        )

        AnimatedVisibility(visible = showTimePicker) {
            Column(
                modifier = Modifier.width(300.dp),
                horizontalAlignment = Alignment.End
            ) {
                CustomTimePicker {
                    timePicked = it
                    onTimeSelected(isAlarmEnable, it)
                }
                GenericButton(
                    buttonType = ButtonType.LOW_EMPHASIS,
                    text = stringResource(R.string.done)
                ) {
                    showTimePicker = !showTimePicker
                }
            }
        }


        AnimatedVisibility(visible = !showTimePicker && isAlarmEnable) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.settings_config_alarm, timePicked),
                    color = MaterialTheme.colors.secondary
                )
            }
        }

    }
}