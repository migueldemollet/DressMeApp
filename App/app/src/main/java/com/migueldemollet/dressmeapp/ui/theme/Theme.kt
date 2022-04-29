package com.migueldemollet.dressmeapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Lilac200,
    primaryVariant = Turquoise700,
    secondary = Teal200,
    background = Black200,
    onSurface = Gray500
)

private val LightColorPalette = lightColors(
    primary = Turquoise200,
    primaryVariant = Turquoise700,
    secondary = Teal200,
    onPrimary = White200,
    onBackground = White200,
    onSurface = Gray200,
    onSecondary = Black200,

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
fun DressMeAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
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