package com.applications.toms.mimetodoplanificado.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.applications.toms.mimetodoplanificado.R

enum class SpacerType {
    VERTICAL,
    HORIZONTAL
}

@Composable
fun GenericSpacer(
    type: SpacerType,
    padding: Dp = dimensionResource(id = R.dimen.no_padding),
    size: Dp = dimensionResource(id = R.dimen.border_xxsmall),
    backgroundColor: Color = Color.Transparent
) {
    when(type){
        SpacerType.VERTICAL -> {
            VerticalSpacer(verticalPadding = padding, size = size, backgroundColor = backgroundColor)
        }
        SpacerType.HORIZONTAL -> {
            HorizontalSpacer(horizontalPadding = padding, size = size, backgroundColor = backgroundColor)
        }
    }
}

@Composable
fun VerticalSpacer(verticalPadding: Dp, size: Dp, backgroundColor: Color) {
    Spacer(modifier = Modifier
        .padding(
            horizontal = dimensionResource(id = R.dimen.no_padding),
            vertical = verticalPadding
        )
        .fillMaxWidth()
        .size(size)
        .background(color = backgroundColor)
    )
}

@Composable
fun HorizontalSpacer(horizontalPadding: Dp, size: Dp, backgroundColor: Color) {
    Spacer(modifier = Modifier
        .padding(
            horizontal = horizontalPadding,
            vertical = dimensionResource(id = R.dimen.no_padding)
        )
        .fillMaxWidth()
        .size(size)
        .background(color = backgroundColor)
    )
}