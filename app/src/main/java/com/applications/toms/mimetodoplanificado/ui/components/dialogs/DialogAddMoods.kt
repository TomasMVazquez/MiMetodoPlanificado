package com.applications.toms.mimetodoplanificado.ui.components.dialogs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.MyLoadingContent
import com.applications.toms.mimetodoplanificado.ui.components.generics.ButtonType
import com.applications.toms.mimetodoplanificado.ui.components.generics.GenericButton
import com.applications.toms.mimetodoplanificado.ui.theme.CarnationPink
import com.applications.toms.mimetodoplanificado.ui.theme.LightBlack
import com.applications.toms.mimetodoplanificado.ui.utils.moods.moodCards

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun DialogAddMoods(
    showDialog: Boolean,
    setShowDialog: (Boolean) -> Unit,
    onSaveMood: (Int) -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = {
                setShowDialog(false)
            },
            properties = DialogProperties(
                dismissOnClickOutside = false
            )
        ) {
            DialogContent(
                onClickCancel = { setShowDialog(false) },
                onClickSave = {
                    onSaveMood(it)
                }
            )
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun DialogContent(onClickCancel: () -> Unit,onClickSave: (Int) -> Unit) {

    var loading by remember { mutableStateOf(false) }
    var painScale by remember { mutableStateOf(-1) }
    val moods = moodCards

    Card() {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
            Text(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium)),
                text = stringResource(R.string.dialog_moods_title),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onPrimary
            )

            LazyVerticalGrid(cells = GridCells.Fixed(3)){
                items(moods) {
                    Card(
                        onClick = { painScale = it.painScale },
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                        backgroundColor = if (painScale == it.painScale) CarnationPink
                        else MaterialTheme.colors.primary
                    ) {
                        Column(
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_tiny)),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            MoodImage(it.icon, it.icon_description, painScale == it.painScale)

                            Text(
                                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small)),
                                text = it.name,
                                style = MaterialTheme.typography.overline,
                                color = if (painScale == it.painScale) MaterialTheme.colors.secondary
                                else MaterialTheme.colors.secondaryVariant
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GenericButton(
                    buttonType = ButtonType.LOW_EMPHASIS,
                    text = stringResource(R.string.dialog_moods_cancel)
                ) {
                    onClickCancel()
                }

                GenericButton(
                    buttonType = ButtonType.HIGH_EMPHASIS,
                    text = stringResource(R.string.dialog_moods_save)
                ) {
                    loading = true
                    onClickSave(painScale)
                }
            }
        }
        if (loading) MyLoadingContent(Modifier.background(color = LightBlack.copy(alpha = 0.5f)))
    }
}