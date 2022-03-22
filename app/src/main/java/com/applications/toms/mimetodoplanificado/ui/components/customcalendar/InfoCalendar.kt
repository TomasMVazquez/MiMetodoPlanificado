package com.applications.toms.mimetodoplanificado.ui.components.customcalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.applications.toms.mimetodoplanificado.R

@Composable
fun InfoCalendar() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_tiny)),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier
                .size(dimensionResource(id = R.dimen.spacer_medium))
                .background(color = MaterialTheme.colors.secondary)
                .padding(horizontal = dimensionResource(id = R.dimen.padding_small)))

            Text(
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
                text = stringResource(id = R.string.info_calendar_cycle),
                style = MaterialTheme.typography.caption
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier
                .size(dimensionResource(id = R.dimen.spacer_medium))
                .background(color = MaterialTheme.colors.secondaryVariant))

            Text(
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
                text = stringResource(id = R.string.info_calendar_break),
                style = MaterialTheme.typography.caption
            )
        }

    }

}