package com.applications.toms.mimetodoplanificado.ui.components.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.applications.toms.mimetodoplanificado.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AlertDialogOkCancel(
    show: Boolean,
    imgVector: ImageVector,
    color: Color,
    title: String,
    subtitle: String = "",
    description: String,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onCancel,
            properties = DialogProperties(usePlatformDefaultWidth = false),
            modifier = Modifier
                .padding(horizontal = 28.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            title = {
                Image(
                    imageVector = imgVector,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(
                        color = color
                    ),
                    modifier = Modifier
                        .height(70.dp)
                        .fillMaxWidth()
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = title,
                        color = MaterialTheme.colors.onPrimary,
                        style = MaterialTheme.typography.h6
                    )
                    if (subtitle.isNotEmpty()) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = dimensionResource(id = R.dimen.spacer_small)),
                            textAlign = TextAlign.Center,
                            text = subtitle,
                            style = MaterialTheme.typography.overline,
                        )
                    }
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = description,
                        style = MaterialTheme.typography.body2,
                    )
                }
            },
            buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    TextButton(
                        modifier = Modifier
                            .weight(0.5f)
                            .background(color.copy(alpha = 0.3f)),
                        onClick = onCancel
                    ) {
                        Text(
                            text = stringResource(R.string.change_my_method_cancel),
                            color = color
                        )
                    }

                    TextButton(
                        modifier = Modifier
                            .weight(0.5f)
                            .background(color.copy(alpha = 0.3f)),
                        onClick = onConfirm
                    ) {
                        Text(
                            text = stringResource(R.string.change_my_method_confirm),
                            color = color
                        )
                    }
                }
            }
        )
    }
}