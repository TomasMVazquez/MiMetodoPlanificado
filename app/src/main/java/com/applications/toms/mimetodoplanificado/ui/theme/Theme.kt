package com.applications.toms.mimetodoplanificado.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = White,
    primaryVariant = White,
    secondary = Purple,
    background =White,
    surface = White,
    onSecondary = Black,
)

private val LightColorPalette = lightColors(
    primary = White,
    primaryVariant = White,
    secondary = Purple,
    background =White,
    surface = White,
//    onPrimary = LightBlack,
    onSecondary = Black,
//    onBackground = Black,
//    onSurface = LightBlack

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
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