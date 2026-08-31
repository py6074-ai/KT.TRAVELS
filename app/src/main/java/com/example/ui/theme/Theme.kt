package com.example.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val KhushiTravelColorScheme = darkColorScheme(
    primary = AmberGold,
    onPrimary = Navy900,
    primaryContainer = RoyalBlue,
    onPrimaryContainer = TextPrimaryLight,
    secondary = SaffronOrange,
    onSecondary = TextPrimaryLight,
    secondaryContainer = DarkSurfaceCardHighlight,
    onSecondaryContainer = AmberGold,
    tertiary = EmeraldGreen,
    onTertiary = Navy900,
    background = Navy900,
    onBackground = TextPrimaryLight,
    surface = DarkSurface,
    onSurface = TextPrimaryLight,
    surfaceVariant = DarkSurfaceCard,
    onSurfaceVariant = TextSecondaryLight,
    outline = DarkSurfaceBorder,
    error = AccentRed,
    onError = TextPrimaryLight
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = KhushiTravelColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Navy900.toArgb()
            window.navigationBarColor = Navy900.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
