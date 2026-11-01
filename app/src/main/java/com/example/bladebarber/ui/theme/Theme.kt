package com.example.bladebarber.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BladeDarkColorScheme = darkColorScheme(
    primary = BladeGold,
    onPrimary = Color.Black,
    primaryContainer = BladeGoldVariant,
    onPrimaryContainer = Color.Black,
    secondary = BladeGold,
    background = BladeDarkBg,
    onBackground = BladeTextPrimary,
    surface = BladeCardBg,
    onSurface = BladeTextPrimary,
    error = BladeError,
    onError = Color.White
)

@Composable
fun BladeBarberTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = BladeDarkColorScheme,
        typography = Typography,
        content = content
    )
}
