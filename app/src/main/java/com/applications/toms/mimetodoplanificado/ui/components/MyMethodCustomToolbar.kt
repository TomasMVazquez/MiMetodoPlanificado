package com.applications.toms.mimetodoplanificado.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChangeCircle
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.applications.toms.mimetodoplanificado.R

@Composable
fun MyMethodCustomToolbar(onChangeMethodClick: () -> Unit, onGoToSettingsClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.padding_large),
                top = dimensionResource(id = R.dimen.no_padding),
                end = dimensionResource(id = R.dimen.no_padding),
                bottom = dimensionResource(id = R.dimen.padding_small)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            onClick = onChangeMethodClick
        ) {
            Icon(
                imageVector = Icons.Outlined.ChangeCircle,
                contentDescription = stringResource(id = R.string.my_method_change)
            )
        }

        IconButton(
            onClick = onGoToSettingsClick
        ) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = stringResource(id = R.string.my_method_settings)
            )
        }
    }

}
