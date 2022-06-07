package com.applications.toms.mimetodoplanificado.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
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
import com.applications.toms.mimetodoplanificado.ui.components.generics.ButtonType
import com.applications.toms.mimetodoplanificado.ui.components.generics.GenericButton
import com.applications.toms.mimetodoplanificado.ui.theme.VividRaspberry
import com.applications.toms.mimetodoplanificado.ui.utils.moods.moodCards
import kotlin.math.round

@Composable
fun DialogAddMoods(showDialog: Boolean, setShowDialog: (Boolean) -> Unit) {
    if (showDialog) {
        Dialog(
            onDismissRequest = {
                setShowDialog(false)
            },
            properties = DialogProperties(
                dismissOnClickOutside = false
            )
        ) {
            DialogContent() {

            }
        }
    }

}

@Composable
fun DialogContent(onClick: () -> Unit) {

    var sliderPosition by remember { mutableStateOf(0f) }
    val moods = moodCards

    Card() {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
            Text(
                text = stringResource(R.string.dialog_moods_title),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onPrimary
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                moods.forEach { mood ->
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (sliderPosition.toInt() == mood.painScale) {
                            MoodImage(mood.icon, mood.icon_description, false)

                            Text(
                                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small)),
                                text = mood.name,
                                style = MaterialTheme.typography.overline,
                                color = VividRaspberry
                            )
                        }
                        when (sliderPosition.toInt()) {
                            in 0..1 -> {
                                if (mood.painScale % 2 == 0 && mood.painScale > 1) {
                                    MoodImage(mood.icon, mood.icon_description, true)
                                }
                            }
                            in 2..3 -> {
                                if (mood.painScale % 2 == 0 && mood.painScale != 2) {
                                    MoodImage(mood.icon, mood.icon_description, true)
                                }
                            }
                            4 -> {
                                if (mood.painScale % 2 == 0 && mood.painScale != 4) {
                                    MoodImage(mood.icon, mood.icon_description, true)
                                }
                            }
                            in 5..6 -> {
                                if (mood.painScale % 2 == 0 && mood.painScale != 6) {
                                    MoodImage(mood.icon, mood.icon_description, true)
                                }
                            }
                            in 7..8 -> {
                                if (mood.painScale % 2 == 0 && mood.painScale != 8) {
                                    MoodImage(mood.icon, mood.icon_description, true)
                                }
                            }
                        }
                    }
                }
            }

            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = round(it) },
                valueRange = 0f..8f,
                colors = SliderDefaults.colors(
                    thumbColor = VividRaspberry,
                    activeTrackColor = VividRaspberry
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GenericButton(
                    buttonType = ButtonType.LOW_EMPHASIS,
                    text = stringResource(R.string.dialog_moods_cancel)
                ) {

                }

                GenericButton(
                    buttonType = ButtonType.HIGH_EMPHASIS,
                    text = stringResource(R.string.dialog_moods_save)
                ) {

                }
            }
        }
    }
}