package com.applications.toms.mimetodoplanificado.ui.components.generics

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.applications.toms.mimetodoplanificado.R

@Composable
fun GenericOutlinedButton(
    icon: ImageVector,
    iconDesc: String? = null,
    onValueChange: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier.size(dimensionResource(id = R.dimen.rounded_icon)),
        onClick = onValueChange,
        border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
        shape = CircleShape,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.secondary
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconDesc
        )
    }
}