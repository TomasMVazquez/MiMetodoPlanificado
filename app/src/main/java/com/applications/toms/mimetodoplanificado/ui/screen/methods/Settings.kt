package com.applications.toms.mimetodoplanificado.ui.screen.methods

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.applications.toms.domain.UserAction
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.ClickableOutlineTextField
import com.applications.toms.mimetodoplanificado.ui.components.CustomCalendarView
import com.applications.toms.mimetodoplanificado.ui.components.InfoSettingsPills
import com.applications.toms.mimetodoplanificado.ui.components.InfoSettingsRing
import com.applications.toms.mimetodoplanificado.ui.components.generics.GenericSpacer
import com.applications.toms.mimetodoplanificado.ui.components.generics.SpacerType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun Settings(methodChosen: UserAction, onDone: () -> Unit) {
    var startDate by rememberSaveable { mutableStateOf(LocalDate.now()) }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var pillsBreakDays by rememberSaveable { mutableStateOf(5) }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(dimensionResource(id = R.dimen.padding_xsmall))) {
        IconButton(onClick = onDone) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.content_description_close),
                tint = MaterialTheme.colors.onBackground
            )
        }

        Column(
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.padding_large),
                vertical = dimensionResource(id = R.dimen.padding_xlarge)
            ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                text = stringResource(R.string.settings_title),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onPrimary
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_small)),
                text = stringResource(R.string.settings_text_start_date),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onPrimary
            )

            ClickableOutlineTextField(input = startDate.format(DateTimeFormatter.ofPattern("dd/MM/yy"))) {
                showDatePicker = !showDatePicker
            }

            AnimatedVisibility(visible = showDatePicker) {
                CustomCalendarView {
                    startDate = it
                    showDatePicker = !showDatePicker
                }
            }

            GenericSpacer(
                type = SpacerType.VERTICAL,
                padding = dimensionResource(id = R.dimen.spacer_xsmall)
            )

            when(methodChosen) {
                UserAction.PILLS -> InfoSettingsPills(
                    startDate = startDate,
                    pillsBreakDays = pillsBreakDays
                ) {
                    pillsBreakDays = it
                }
                UserAction.RING -> InfoSettingsRing(startDate = startDate)
                UserAction.SHOOT -> TODO()
                UserAction.PATCH -> TODO()
            }
        }
    }
}
