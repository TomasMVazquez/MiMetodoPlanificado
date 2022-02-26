package com.applications.toms.mimetodoplanificado.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.applications.toms.domain.MethodCard
import com.applications.toms.domain.enums.Method
import com.applications.toms.domain.enums.UserAction

@ExperimentalMaterialApi
@Composable
fun CardButton(method: MethodCard, onClick: (Method, UserAction) -> Unit) {
    Card(
        onClick = { onClick(method.method, method.action) },
        modifier = Modifier.padding(16.dp),
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.primary
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
                tint = MaterialTheme.colors.secondary
            )

            Text(
                modifier = Modifier.padding(8.dp),
                text = method.name,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}