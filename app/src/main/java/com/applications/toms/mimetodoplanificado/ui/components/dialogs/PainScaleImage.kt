package com.applications.toms.mimetodoplanificado.ui.components.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun PainScaleImage(painScaleIcon: Int, painScaleContDesc: String, filterGray: Boolean) {
    Image(
        modifier = Modifier
            .size(40.dp)
            .background(color = Color.Transparent),
        painter = painterResource(id = painScaleIcon),
        contentDescription = painScaleContDesc,
        colorFilter = if (filterGray) ColorFilter.tint(Color.Gray) else null
    )
}