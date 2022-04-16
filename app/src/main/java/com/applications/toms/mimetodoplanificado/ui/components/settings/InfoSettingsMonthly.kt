package com.applications.toms.mimetodoplanificado.ui.components.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.utils.toFormattedString
import java.time.LocalDate

@Composable
fun InfoSettingsMonthly(startDate: LocalDate, onOptionSelected: (Int) -> Unit) {

    var optionSelected by remember { mutableStateOf(0) }

    if (optionSelected != 0 ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = R.dimen.padding_medium),
                    start = dimensionResource(id = R.dimen.padding_medium)
                ),
            text = stringResource(
                id = if (optionSelected == 30) R.string.settings_shoot_monthly_start_date_text
                else R.string.settings_shoot_quarterly_start_date_text,
                startDate.toFormattedString()
            ),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onPrimary
        )
    }

    Row(verticalAlignment = Alignment.CenterVertically) {

        RadioButton(
            selected = optionSelected == 30,
            onClick = {
                optionSelected = 30
                onOptionSelected(optionSelected)
            }
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = dimensionResource(id = R.dimen.padding_small)),
            text = stringResource(id = R.string.settings_shoot_monthly),
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onPrimary
        )
    }

    Row(verticalAlignment = Alignment.CenterVertically) {

        RadioButton(
            selected = optionSelected == 90,
            onClick = {
                optionSelected = 90
                onOptionSelected(optionSelected)
            }
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = dimensionResource(id = R.dimen.padding_small)),
            text = stringResource(id = R.string.settings_shoot_quarterly),
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onPrimary
        )
    }

}