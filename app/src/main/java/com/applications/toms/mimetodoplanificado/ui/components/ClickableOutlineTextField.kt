package com.applications.toms.mimetodoplanificado.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.theme.Shapes

@Composable
fun ClickableOutlineTextField(input: String, onClick: () -> Unit ) {

    OutlinedTextField(
        value = input,
        onValueChange = { },
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        enabled = false,
        maxLines = 1,
        shape = Shapes.medium,
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.CalendarToday,
                contentDescription = stringResource(R.string.content_desc_calendar),
                tint = MaterialTheme.colors.onBackground
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            disabledTextColor = MaterialTheme.colors.onBackground,
            unfocusedIndicatorColor = MaterialTheme.colors.onBackground,
            disabledIndicatorColor = MaterialTheme.colors.onBackground,
            backgroundColor = Color.Transparent
        )
    )
}