package com.applications.toms.mimetodoplanificado.ui.components.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
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
fun NotificationSettingsItem(
    isEnable: Boolean = false,
    timeSet: String? = null,
    onTimeSelected: (Boolean, String?) -> Unit
) {
    var showTimePicker by rememberSaveable { mutableStateOf(false) }
    var isNotificationEnable by rememberSaveable { mutableStateOf(isEnable) }
    var timePicked by rememberSaveable {
        mutableStateOf(
            if (isEnable && timeSet != null)
                timeSet
            else
                "${decimalFormat.format(LocalDateTime.now().hour)}:00"
        )
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        GenericSwitchSetting(
            title = stringResource(R.string.settings_notifications_title),
            info = stringResource(R.string.settings_notification_info),
            input = isNotificationEnable
        ) {
            isNotificationEnable = it
            showTimePicker = it
            onTimeSelected(isNotificationEnable, if (it) timePicked else null)
        }

        GenericSpacer(
            type = SpacerType.VERTICAL,
            padding = dimensionResource(id = R.dimen.padding_small)
        )

        AnimatedVisibility(visible = showTimePicker) {
            Column(
                modifier = Modifier.width(300.dp),
                horizontalAlignment = Alignment.End
            ) {
                CustomTimePicker(timeSet = timeSet) {
                    timePicked = it
                    onTimeSelected(isNotificationEnable, it)
                }
                GenericButton(
                    buttonType = ButtonType.LOW_EMPHASIS,
                    text = stringResource(R.string.done)
                ) {
                    showTimePicker = !showTimePicker
                    onTimeSelected(isNotificationEnable, if (isNotificationEnable) timePicked else null)
                }
            }
        }


        AnimatedVisibility(visible = !showTimePicker && isNotificationEnable) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.settings_config_notif, timePicked),
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.body1
                )

                IconButton(
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_tiny)),
                    onClick = {showTimePicker = !showTimePicker}
                ) {
                    Icon(
                        imageVector = Icons.Default.ExpandMore,
                        contentDescription = stringResource(R.string.content_description_expand_more),
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            }
        }

    }
}