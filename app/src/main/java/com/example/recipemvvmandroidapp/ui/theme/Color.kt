package com.example.recipemvvmandroidapp.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.ui.graphics.Color

val DarkBackground = Color(0xFF001F38)
val DarkBackgroundVariant = Color(0xFF00355E)
val LightBackground = Color(0xFF00A6BB)
val LightText = Color(0xFF93F3FF)

val DarkColorPalette = darkColors(
    primary = DarkBackground,
    surface = DarkBackgroundVariant,
    onSurface = LightText,
    background = DarkBackgroundVariant,
    onBackground = LightBackground
)