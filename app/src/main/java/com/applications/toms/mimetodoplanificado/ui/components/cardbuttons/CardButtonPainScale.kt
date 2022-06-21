package com.applications.toms.mimetodoplanificado.ui.components.cardbuttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.applications.toms.mimetodoplanificado.R

@ExperimentalMaterialApi
@Composable
fun CardButtonPainScale(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = modifier
            .padding(end = dimensionResource(id = R.dimen.padding_medium))
            .offset(y = (-dimensionResource(id = R.dimen.padding_large))),
        elevation = dimensionResource(id = R.dimen.card_elevation)
    ) {
        Image(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.icon_size))
                .background(color = MaterialTheme.colors.primary.copy(alpha = 0.5f))
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            painter = painterResource(id = R.drawable.ic_pain_scale),
            contentDescription = stringResource(R.string.ic_content_desc_pain_scale)
        )
    }
}