package com.applications.toms.mimetodoplanificado.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.AlarmOff
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.NotificationsOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.applications.toms.mimetodoplanificado.R

@Composable
fun InfoNotificationsAndAlarm(alarm: Boolean?, alarmTime: String?, notifications: Boolean?, notificationTime: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_tiny)),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        alarm?.let {
            Row(
                modifier = Modifier.weight(0.5f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (it) Icons.Outlined.Alarm else Icons.Outlined.AlarmOff,
                    contentDescription = stringResource(id = R.string.my_method_alarm),
                    tint = if (it) MaterialTheme.colors.secondary else MaterialTheme.colors.onPrimary
                )

                Text(
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
                    text = if (it) stringResource(
                        id = R.string.settings_config_alarm,
                        alarmTime ?: ""
                    ) else stringResource(id = R.string.settings_config_alarm_off),
                    style = MaterialTheme.typography.caption,
                    maxLines = 2
                )
            }
        }

        notifications?.let {
            Row(
                modifier = Modifier.weight(0.5f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (it) Icons.Outlined.Notifications else Icons.Outlined.NotificationsOff,
                    contentDescription = stringResource(id = R.string.my_method_notif),
                    tint = if (it) MaterialTheme.colors.secondary else MaterialTheme.colors.onPrimary
                )

                Text(
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
                    text = if (it) stringResource(
                        id = R.string.settings_config_notif,
                        notificationTime ?: ""
                    ) else stringResource(id = R.string.settings_config_notif_off),
                    style = MaterialTheme.typography.caption,
                    maxLines = 2
                )
            }
        }

    }
}
