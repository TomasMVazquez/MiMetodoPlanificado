package com.applications.toms.mimetodoplanificado.ui.components.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.utils.methods.TOTAL_CYCLE_DAYS
import com.applications.toms.mimetodoplanificado.ui.utils.toFormattedString
import java.time.LocalDate

@Composable
fun InfoSettingsMyCycle(startDate: LocalDate) {
    Column(modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_xlarge))) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = R.dimen.padding_medium),
                    start = dimensionResource(id = R.dimen.padding_medium)
                ),
            text = stringResource(
                id = R.string.settings_cycle_start_date_text,
                startDate.toFormattedString()
            ),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onPrimary
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = dimensionResource(id = R.dimen.padding_medium)),
            text = stringResource(
                id = R.string.settings_cycle_next_date_text,
                startDate.plusDays(TOTAL_CYCLE_DAYS).toFormattedString()
            ),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onPrimary
        )
    }
}