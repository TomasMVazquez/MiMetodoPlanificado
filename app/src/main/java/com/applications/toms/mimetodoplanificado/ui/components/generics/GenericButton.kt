package com.applications.toms.mimetodoplanificado.ui.components.generics

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonDefaults.textButtonColors
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.applications.toms.mimetodoplanificado.ui.theme.Shapes
import com.applications.toms.mimetodoplanificado.ui.theme.VividRaspberry

enum class ButtonType {
    HIGH_EMPHASIS,
    MEDIUM_EMPHASIS,
    LOW_EMPHASIS
}

@Composable
fun GenericButton(
    buttonType: ButtonType,
    modifier: Modifier = Modifier,
    text: String,
    withIcon: Boolean = false,
    icon: ImageVector = Icons.Filled.Favorite,
    contentDesc: String? = null,
    enable: Boolean = true,
    onClick: () -> Unit
){

    when(buttonType){
        ButtonType.HIGH_EMPHASIS ->
            HighEmphasisButton(
                modifier = modifier,
                text = text,
                withIcon = withIcon,
                icon = icon,
                contentDesc = contentDesc,
                enable = enable,
                onClick = onClick
            )
        ButtonType.MEDIUM_EMPHASIS ->
            MediumEmphasisButton(
                modifier = modifier,
                text = text,
                withIcon = withIcon,
                icon = icon,
                contentDesc = contentDesc,
                enable = enable,
                onClick = onClick
            )
        ButtonType.LOW_EMPHASIS ->
            LowEmphasisButton(
                modifier = modifier,
                text = text,
                enable = enable,
                onClick = onClick
            )
    }
}

@Composable
fun HighEmphasisButton(
    modifier: Modifier,
    text: String,
    withIcon: Boolean,
    icon: ImageVector,
    contentDesc: String?,
    enable: Boolean,
    onClick: () -> Unit
){
    Button(
        modifier = modifier,
        onClick = onClick,
        contentPadding = PaddingValues(
            horizontal = 20.dp,
            vertical = 12.dp
        ),
        shape = Shapes.medium,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.onSecondary
        ),
        enabled = enable
    ) {
        if (withIcon){
            Icon(
                imageVector = icon,
                contentDescription = contentDesc,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        }
        Text(text)

    }
}

@Composable
fun MediumEmphasisButton(
    modifier: Modifier,
    text: String,
    withIcon: Boolean,
    icon: ImageVector,
    contentDesc: String?,
    enable: Boolean,
    onClick: () -> Unit
){
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        contentPadding = PaddingValues(
            horizontal = 20.dp,
            vertical = 12.dp
        ),
        shape = Shapes.medium,
        border = BorderStroke(
            1.dp,
            color = MaterialTheme.colors.primary
        ),
        enabled = enable
    ) {
        if (withIcon){
            Icon(
                imageVector = icon,
                contentDescription = contentDesc,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        }
        Text(text)
    }
}

@Composable
fun LowEmphasisButton(
    modifier: Modifier,
    text: String,
    enable: Boolean,
    onClick: () -> Unit
){
    TextButton(
        modifier = modifier,
        onClick = onClick,
        contentPadding = PaddingValues(
            horizontal = 20.dp,
            vertical = 12.dp
        ),
        colors = textButtonColors(
            contentColor = VividRaspberry
        ),
        enabled = enable
    ) {
        Text(text)
    }
}

@Preview
@Composable
fun HighEmphasisButtonPrev(){
    Button(
        onClick = {},
        contentPadding = PaddingValues(
            horizontal = 20.dp,
            vertical = 12.dp
        ),
        shape = Shapes.medium
    ) {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "Favorite",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text("Like")

    }
}

@Preview
@Composable
fun MediumEmphasisButtonPrev(){
    OutlinedButton(
        onClick = {  },
        contentPadding = PaddingValues(
            horizontal = 20.dp,
            vertical = 12.dp
        ),
        shape = Shapes.medium,
        border = BorderStroke(
            1.dp,
            color = MaterialTheme.colors.primary
        )
    ) {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "Favorite",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text("Like")
    }
}

@Preview
@Composable
fun LowEmphasisButtonPrev(){
    TextButton(
        onClick = {  },
        contentPadding = PaddingValues(
            horizontal = 20.dp,
            vertical = 12.dp
        )
    ) {
        Text("Like")
    }
}