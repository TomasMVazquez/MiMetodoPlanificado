package com.applications.toms.mimetodoplanificado.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.screen.methods.TOTAL_CYCLE_DAYS
import com.applications.toms.mimetodoplanificado.ui.utils.toFormattedString
import java.time.LocalDate

@Composable
fun InfoSettingsPills(startDate: LocalDate, pillsBreakDays: Int, pillsBreakDaysChange: (Int) -> Unit) {

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.padding_small)),
        text = stringResource(
            id = R.string.settings_pills_start_date_text,
            startDate.toFormattedString()
        ),
        style = MaterialTheme.typography.body2,
        color = MaterialTheme.colors.onPrimary
    )

    PlusMinusNumber(input = pillsBreakDays, onInputChange = pillsBreakDaysChange)

    Text(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.padding_small)),
        text = stringResource(
            id = R.string.settings_ring_next_date_text,
            startDate.plusDays(TOTAL_CYCLE_DAYS).toFormattedString()
        ),
        style = MaterialTheme.typography.body2,
        color = MaterialTheme.colors.onPrimary
    )
}