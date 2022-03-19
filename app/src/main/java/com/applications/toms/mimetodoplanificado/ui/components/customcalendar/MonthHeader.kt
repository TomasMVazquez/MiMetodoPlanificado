package com.applications.toms.mimetodoplanificado.ui.components.customcalendar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.applications.toms.mimetodoplanificado.R

@Composable
fun MonthHeader(modifier: Modifier = Modifier, month: String, year: String) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = dimensionResource(id = R.dimen.padding_xsmall))
    ) {
        Text(
            modifier = Modifier,
            text = month,
            style = MaterialTheme.typography.body2
        )
        Spacer(Modifier.width(dimensionResource(id = R.dimen.spacer_large)))
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = year,
            style = MaterialTheme.typography.caption
        )
    }
}