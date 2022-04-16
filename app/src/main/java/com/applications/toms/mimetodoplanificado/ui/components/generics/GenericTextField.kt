package com.applications.toms.mimetodoplanificado.ui.components.generics

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.applications.toms.mimetodoplanificado.ui.theme.Shapes

enum class TextFieldType{
    SIMPLE_TEXT_FIELD,
    OUTLINED_TEXT_FIELD,
    TRANSPARENT_TEXT_FIELD
}

@Composable
fun GenericTextField(
    modifier: Modifier,
    textFieldType: TextFieldType,
    input: String,
    label: String = "",
    maxLines: Int,
    keyboardOptions: KeyboardOptions
){
    when(textFieldType){
        TextFieldType.SIMPLE_TEXT_FIELD -> SimpleEditText(
            modifier = modifier,
            input = input,
            label = label,
            maxLines = maxLines,
            keyboardOptions = keyboardOptions
        )
        TextFieldType.OUTLINED_TEXT_FIELD -> OutlinedTextField(
            modifier = modifier,
            input = input,
            label = label,
            maxLines = maxLines,
            keyboardOptions = keyboardOptions
        )
        TextFieldType.TRANSPARENT_TEXT_FIELD -> TransparentTextField(
            modifier = modifier,
            input = input,
            label = label,
            maxLines = maxLines,
            keyboardOptions = keyboardOptions
        )
    }
}


@Composable
fun SimpleEditText(
    modifier: Modifier,
    input: String,
    label: String,
    maxLines: Int,
    keyboardOptions: KeyboardOptions
){
    var text by rememberSaveable { mutableStateOf(input) }

    TextField(
        value = text,
        onValueChange = { newText ->
            text = newText.trimStart { it == '0' || it == ' '}
        },
        label = { if(label.isNotEmpty()) Text(label) },
        modifier = modifier.fillMaxWidth(),
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,
        trailingIcon = {
            if (text.isNotEmpty()){
                IconButton(onClick = { text = "" }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Composable
fun OutlinedTextField(
    modifier: Modifier,
    input: String,
    label: String,
    maxLines: Int,
    keyboardOptions: KeyboardOptions
){
    var text by rememberSaveable { mutableStateOf(input) }

    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            text = newText.trimStart { it == '0' || it == ' '}
        },
        label = { if(label.isNotEmpty()) Text(label) },
        modifier = modifier.fillMaxWidth(),
        shape = Shapes.medium,
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,
        trailingIcon = {
            if (text.isNotEmpty()){
                IconButton(onClick = { text = "" }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Composable
fun TransparentTextField(
    modifier: Modifier,
    input: String,
    label: String,
    maxLines: Int,
    keyboardOptions: KeyboardOptions
){
    var text by rememberSaveable { mutableStateOf(input) }

    TextField(
        value = text,
        onValueChange = { newText ->
            text = newText.trimStart { it == '0' || it == ' '}
        },
        label = { if(label.isNotEmpty()) Text(label) },
        modifier = modifier.fillMaxWidth(),
        shape = Shapes.medium,
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,
        trailingIcon = {
            if (text.isNotEmpty()){
                IconButton(onClick = { text = "" }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null
                    )
                }
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}
