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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChangeCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.generics.GenericSpacer
import com.applications.toms.mimetodoplanificado.ui.components.generics.SpacerType

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AlertDialogConfirmMethodChange(onCancel: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .padding(horizontal = 28.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        title = {
            Image(
                imageVector = Icons.Outlined.ChangeCircle,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colors.secondary
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
                    text = stringResource(R.string.change_my_method_title),
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(id = R.dimen.spacer_small)),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.change_my_method_warning),
                    style = MaterialTheme.typography.overline,
                )
                GenericSpacer(
                    type = SpacerType.VERTICAL,
                    padding = dimensionResource(id = R.dimen.spacer_small)
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.change_my_method_description),
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
                        .background(MaterialTheme.colors.secondary.copy(alpha = 0.3f)),
                    onClick = onCancel
                ) {
                    Text(
                        text = stringResource(R.string.change_my_method_cancel),
                        color = MaterialTheme.colors.secondary
                    )
                }

                TextButton(
                    modifier = Modifier
                        .weight(0.5f)
                        .background(MaterialTheme.colors.secondary.copy(alpha = 0.3f)),
                    onClick = onConfirm
                ) {
                    Text(
                        text = stringResource(R.string.change_my_method_confirm),
                        color = MaterialTheme.colors.secondary
                    )
                }
            }
        }
    )
}