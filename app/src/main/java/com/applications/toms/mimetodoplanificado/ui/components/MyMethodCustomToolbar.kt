package com.applications.toms.mimetodoplanificado.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.ChangeCircle
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.applications.toms.mimetodoplanificado.R

@Composable
fun MyMethodCustomToolbar(
    title: String? = null,
    isOnlyCycle: Boolean,
    onChangeMethodClick: () -> Unit,
    onGoToAlarmSettingsClick: () -> Unit,
    onGoToAnalyticsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.padding_small),
                top = dimensionResource(id = R.dimen.no_padding),
                end = dimensionResource(id = R.dimen.no_padding),
                bottom = dimensionResource(id = R.dimen.padding_small)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (!title.isNullOrEmpty()) {
            Text(
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium)),
                text = title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            Box(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentAlignment = if (!isOnlyCycle) Alignment.CenterStart else Alignment.CenterEnd
            ){
                IconButton(
                    onClick = onChangeMethodClick
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ChangeCircle,
                        contentDescription = stringResource(id = R.string.my_method_change)
                    )
                }
            }

            IconButton(
                onClick = onGoToAnalyticsClick
            ) {
                Icon(
                    imageVector = Icons.Outlined.BarChart,
                    contentDescription = stringResource(id = R.string.my_method_settings)
                )
            }

            if (!isOnlyCycle){
                IconButton(
                    onClick = onGoToAlarmSettingsClick
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = stringResource(id = R.string.my_method_settings)
                    )
                }
            }
        }
    }
}
