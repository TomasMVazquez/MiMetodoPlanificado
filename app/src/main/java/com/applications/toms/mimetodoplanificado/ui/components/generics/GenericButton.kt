package com.applications.toms.mimetodoplanificado.ui.components.generics

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.ButtonDefaults.textButtonColors
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
                onClick = onClick
            )
        ButtonType.MEDIUM_EMPHASIS ->
            MediumEmphasisButton(
                modifier = modifier,
                text = text,
                withIcon = withIcon,
                icon = icon,
                contentDesc = contentDesc,
                onClick = onClick
            )
        ButtonType.LOW_EMPHASIS ->
            LowEmphasisButton(
                modifier = modifier,
                text = text,
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
    onClick: () -> Unit
){
    Button(
        modifier = modifier,
        onClick = onClick,
        contentPadding = PaddingValues(
            horizontal = 20.dp,
            vertical = 12.dp
        ),
        shape = Shapes.medium
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
        )
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
        )
    ) {
        Text(text)
    }
}

@Preview
@Composable
fun HighEmphasisButtonPrev(){
    Button(
        onClick = { /*TODO*/ },
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
        onClick = { /*TODO*/ },
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
        onClick = { /*TODO*/ },
        contentPadding = PaddingValues(
            horizontal = 20.dp,
            vertical = 12.dp
        )
    ) {
        Text("Like")
    }
}