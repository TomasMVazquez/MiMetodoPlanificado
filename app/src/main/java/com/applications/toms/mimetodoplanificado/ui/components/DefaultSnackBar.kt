package com.applications.toms.mimetodoplanificado.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDefaults
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.applications.toms.mimetodoplanificado.ui.theme.ErrorRed
import com.applications.toms.mimetodoplanificado.ui.theme.Info
import com.applications.toms.mimetodoplanificado.ui.theme.Shapes
import com.applications.toms.mimetodoplanificado.ui.theme.Success
import com.applications.toms.mimetodoplanificado.ui.theme.Warning

enum class SnackBarType(val channel: Int) {
    SUCCESS(1),
    ERROR(2),
    INFO(3),
    WARNING(4),
    DEFAULT(0)
}

@Composable
fun DefaultSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    snackBarType: SnackBarType = SnackBarType.DEFAULT,
    onDismiss: () -> Unit = { }
) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                shape = Shapes.medium,
                backgroundColor = when(snackBarType) {
                    SnackBarType.SUCCESS -> Success
                    SnackBarType.ERROR -> ErrorRed
                    SnackBarType.INFO -> Info
                    SnackBarType.WARNING -> Warning
                    SnackBarType.DEFAULT -> SnackbarDefaults.backgroundColor
                },
                content = {
                    Text(
                        text = data.message,
                        style = MaterialTheme.typography.body2
                    )
                },
                action = {
                    data.actionLabel?.let { actionLabel ->
                        TextButton(onClick = onDismiss) {
                            Text(
                                text = actionLabel,
                                color= MaterialTheme.colors.primary,
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }
                }
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Bottom)
    )
}