package com.applications.toms.mimetodoplanificado.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.generics.GenericOutlinedButton

@Composable
fun PlusMinusNumber(input: Int, onInputChange: (Int) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = dimensionResource(id = R.dimen.padding_small)),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        GenericOutlinedButton(
            icon = Icons.Filled.Remove,
            iconDesc = stringResource(R.string.content_description_remove)
        ) {
            if (input > 0) onInputChange(input.minus(1))
        }

        Column(modifier = Modifier
            .size(dimensionResource(id = R.dimen.rounded_icon))
            .border(
                BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colors.onBackground
                )
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = input.toString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.secondary
            )
        }

        GenericOutlinedButton(
            icon = Icons.Filled.Add,
            iconDesc = stringResource(R.string.content_description_add)
        ) {
            onInputChange(input.plus(1))
        }

    }

}