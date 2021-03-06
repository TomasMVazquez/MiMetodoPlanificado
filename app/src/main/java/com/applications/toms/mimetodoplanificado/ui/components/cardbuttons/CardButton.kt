package com.applications.toms.mimetodoplanificado.ui.components.cardbuttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.applications.toms.domain.MethodCard
import com.applications.toms.domain.enums.Method
import com.applications.toms.domain.enums.UserAction
import com.applications.toms.mimetodoplanificado.R

@ExperimentalMaterialApi
@Composable
fun CardButton(method: MethodCard, onClick: (Method, UserAction) -> Unit) {
    Card(
        onClick = { onClick(method.method, method.action) },
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
        elevation = dimensionResource(id = R.dimen.card_elevation),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                painter = painterResource(id = method.icon),
                contentDescription = method.icon_description,
                tint = MaterialTheme.colors.secondary
            )

            Text(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                text = method.name,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}