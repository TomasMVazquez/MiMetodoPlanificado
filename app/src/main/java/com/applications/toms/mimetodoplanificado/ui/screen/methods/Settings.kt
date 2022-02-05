package com.applications.toms.mimetodoplanificado.ui.screen.methods

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.applications.toms.mimetodoplanificado.R

@Composable
fun Settings(onDone: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().padding(dimensionResource(id = R.dimen.padding_xsmall))) {
        IconButton(onClick = onDone) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.content_description_close),
                tint = MaterialTheme.colors.onBackground
            )
        }
    }
}