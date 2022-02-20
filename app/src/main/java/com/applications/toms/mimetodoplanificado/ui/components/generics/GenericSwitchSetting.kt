package com.applications.toms.mimetodoplanificado.ui.components.generics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun GenericSwitchSetting(
    title: String,
    info: String? = null,
    input: Boolean,
    onValueChange: (Boolean) -> Unit
) {

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = title //stringResource(R.string.settings_notifications_title)
            )
            Switch(
                checked = input,
                onCheckedChange = onValueChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colors.secondary,
                    uncheckedThumbColor = MaterialTheme.colors.onPrimary,
                    checkedTrackColor = MaterialTheme.colors.secondary.copy(alpha = 0.8f),
                    uncheckedTrackColor = MaterialTheme.colors.onPrimary.copy(alpha = 0.8f)
                )
            )
        }
        info?.let {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = it,//stringResource(R.string.settings_notification_info),
                style = MaterialTheme.typography.overline,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}