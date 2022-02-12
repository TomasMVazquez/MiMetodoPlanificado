package com.applications.toms.mimetodoplanificado.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = Cottage,
    onPrimary = LightBlack,
    primaryVariant = White,
    secondary = Purple,
    onSecondary = White,
    background = Cottage,
    onBackground = LightBlack,
    onError = ErrorRed
)

private val DarkColorPalette = darkColors(
    primary = LightBlack,
    onPrimary = Cottage,
    primaryVariant = LightBlack,
    secondary = VividRaspberry,
    onSecondary = White,
    background = LightBlack,
    onBackground = Cottage,
    onError = ErrorRed
)

@Composable
fun MiMetodoPlanificadoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}