package com.applications.toms.mimetodoplanificado.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MyLoadingContent(modifier: Modifier = Modifier, contentAlignment: Alignment = Alignment.Center,) {
    Box(
        modifier = modifier,
        contentAlignment = contentAlignment
    ){
        CircularProgressIndicator(
            color = MaterialTheme.colors.secondary
        )
    }
}