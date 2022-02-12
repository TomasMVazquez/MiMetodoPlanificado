package com.applications.toms.mimetodoplanificado.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.applications.toms.mimetodoplanificado.R

@Composable
fun PlusMinusNumber(input: Int, onInputChange: (Int) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                all = dimensionResource(id = R.dimen.padding_small)
            ),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        OutlinedButton(
            modifier = Modifier.size(dimensionResource(id = R.dimen.rounded_icon)),
            onClick = { if (input > 0) onInputChange(input.minus(1)) },
            border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = MaterialTheme.colors.onBackground,
                contentColor = MaterialTheme.colors.secondary
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Remove,
                contentDescription = stringResource(
                    R.string.content_description_remove
                )
            )
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
                color = MaterialTheme.colors.onPrimary
            )
        }

        OutlinedButton(
            modifier = Modifier.size(dimensionResource(id = R.dimen.rounded_icon)),
            onClick = { onInputChange(input.plus(1)) },
            border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = MaterialTheme.colors.onBackground,
                contentColor = MaterialTheme.colors.secondary
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(
                    R.string.content_description_add
                )
            )
        }

    }

}