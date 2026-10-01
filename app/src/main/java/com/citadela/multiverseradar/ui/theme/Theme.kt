package com.citadela.multiverseradar.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Paleta "portal verde" inspirada no multiverso
val PortalGreen = Color(0xFF97CE4C)
val PortalGreenDark = Color(0xFF3A6B1E)
val SpaceBlack = Color(0xFF0E1116)
val CardSurface = Color(0xFF1A1F27)
val StatusAlive = Color(0xFF55CC44)
val StatusDead = Color(0xFFD63D2E)
val StatusUnknown = Color(0xFF9E9E9E)

private val MultiverseColors = darkColorScheme(
    primary = PortalGreen,
    onPrimary = SpaceBlack,
    primaryContainer = PortalGreenDark,
    secondary = Color(0xFF44C3E0),
    background = SpaceBlack,
    onBackground = Color(0xFFE8EAED),
    surface = CardSurface,
    onSurface = Color(0xFFE8EAED),
    surfaceVariant = Color(0xFF252B35),
    onSurfaceVariant = Color(0xFFB0B6BF),
    error = Color(0xFFFF6B5E),
    errorContainer = Color(0xFF4A1C18),
    onErrorContainer = Color(0xFFFFDAD5)
)

@Composable
fun MultiverseRadarTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = MultiverseColors, content = content)
}
