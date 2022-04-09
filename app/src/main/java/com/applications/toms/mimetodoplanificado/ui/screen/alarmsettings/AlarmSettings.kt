package com.applications.toms.mimetodoplanificado.ui.screen.alarmsettings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.settings.AlarmSettingsItem
import com.applications.toms.mimetodoplanificado.ui.components.settings.NotificationSettingsItem

@Composable
fun AlarmSettings(goBack: () -> Unit) {

    AlarmSettingsContent(goBack = goBack)
}

@Composable
fun AlarmSettingsContent(goBack: () -> Unit) {
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
                    isEnable = true,
                    timeSet = "10:25"
                ) { isNotifEnable, time ->
                    /*viewModel.changeNotificationValue(
                        value = isNotifEnable,
                        time = time
                    )*/
                }
            }

            item {
                AlarmSettingsItem(
                    isEnable = true,
                    timeSet = "14:33"
                ) { isAlarmEnabled, time ->
                    /*viewModel.changeAlarmValue(
                        value = isAlarmEnabled,
                        time = time
                    )*/
                }
            }

        }
    }
}