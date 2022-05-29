package com.applications.toms.mimetodoplanificado.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.applications.toms.mimetodoplanificado.R

@Composable
fun EmptyStateComponent(textToShow: String = "") {
    Column(
        modifier = Modifier.fillMaxWidth().padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.obg_choose),
            contentDescription = stringResource(R.string.obg_img_desc_choose),
            modifier = Modifier
                .size(size = dimensionResource(id = R.dimen.onboarding_image_size))
                .padding(vertical = dimensionResource(id = R.dimen.padding_large))
        )

        Text(
            text = textToShow,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center
        )
    }
}