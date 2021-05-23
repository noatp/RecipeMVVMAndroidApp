package com.example.recipemvvmandroidapp.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.ui.graphics.Color

val DarkBackground = Color(0xFF001F38)
val DarkBackgroundVariant = Color(0xFF00355E)
val LightBackground = Color(0xFF00A6BB)
val DarkText = Color(0xFF004852)
val LightText = Color(0xFF93F3FF)
val White = Color(0xFF93F3FF)
val Black = Color(0x00000000)

val DarkColorPalette = darkColors(
    primary = DarkBackground,
    surface = DarkBackgroundVariant,
    onSurface = LightText,
    background = Black,
    onBackground = LightText
)