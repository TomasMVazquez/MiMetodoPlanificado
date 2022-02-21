package com.applications.toms.mimetodoplanificado.ui.components.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.ClickableOutlineTextField
import com.applications.toms.mimetodoplanificado.ui.components.CustomCalendarView
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DatePickerSettingsItem(date: LocalDate, onDateChange: (LocalDate) -> Unit) {
    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small)),
            text = stringResource(R.string.settings_text_start_date),
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onPrimary
        )

        ClickableOutlineTextField(input = date.format(DateTimeFormatter.ofPattern("dd/MM/yy"))) {
            showDatePicker = !showDatePicker
        }

        AnimatedVisibility(visible = showDatePicker) {
            CustomCalendarView {
                onDateChange(it)
                showDatePicker = !showDatePicker
            }
        }
    }

}