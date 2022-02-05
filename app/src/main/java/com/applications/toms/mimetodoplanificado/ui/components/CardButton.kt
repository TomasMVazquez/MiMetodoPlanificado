package com.applications.toms.mimetodoplanificado.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.applications.toms.domain.MethodCard
import com.applications.toms.domain.UserAction
import com.applications.toms.mimetodoplanificado.ui.theme.Cottage
import com.applications.toms.mimetodoplanificado.ui.theme.Purple
import com.applications.toms.mimetodoplanificado.ui.theme.VividRaspberry

@ExperimentalMaterialApi
@Composable
fun CardButton(method: MethodCard, onClick: (UserAction) -> Unit) {
    Card(
        onClick = { onClick(method.action) },
        modifier = Modifier.padding(16.dp),
        elevation = 8.dp,
        backgroundColor = Cottage
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                modifier = Modifier.padding(8.dp),
                painter = painterResource(id = method.icon),
                contentDescription = method.icon_description,
                tint = Purple
            )

            Text(
                modifier = Modifier.padding(8.dp),
                text = method.name,
                style = MaterialTheme.typography.h6
            )
        }
    }
}