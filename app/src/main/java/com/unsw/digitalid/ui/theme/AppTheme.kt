package com.unsw.digitalid.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ─── UNSW Brand Colours ────────────────────────────────────────────────────
val UnswYellow       = Color(0xFFFFD100)   // UNSW official primary yellow
val UnswYellowLight  = Color(0xFFFFE566)   // lighter tint for gradients
val UnswYellowDark   = Color(0xFFD4AC00)   // deeper tone for pressed states
val UnswNavy         = Color(0xFF003087)   // UNSW official dark navy
val UnswNavyDark     = Color(0xFF001F5C)   // deeper navy for dark surfaces
val UnswGold         = Color(0xFFB8960C)   // warm gold accent
val UnswWhite        = Color(0xFFFFFFFF)
val UnswOffWhite     = Color(0xFFF8F8F6)   // warm off-white backgrounds
val UnswLightGrey    = Color(0xFFE8E8E8)
val UnswMidGrey      = Color(0xFF9E9E9E)
val UnswDarkGrey     = Color(0xFF424242)
val UnswSurface      = Color(0xFFFFFFFF)
val UnswError        = Color(0xFFB00020)

// ─── Light Colour Scheme ────────────────────────────────────────────────────
private val LightColorScheme = lightColorScheme(
    primary              = UnswNavy,
    onPrimary            = UnswWhite,
    primaryContainer     = UnswYellow,
    onPrimaryContainer   = UnswNavy,
    secondary            = UnswYellowDark,
    onSecondary          = UnswNavy,
    secondaryContainer   = UnswYellowLight,
    onSecondaryContainer = UnswNavy,
    tertiary             = UnswGold,
    background           = UnswWhite,
    onBackground         = UnswDarkGrey,
    surface              = UnswSurface,
    onSurface            = UnswDarkGrey,
    surfaceVariant       = UnswOffWhite,
    onSurfaceVariant     = UnswDarkGrey,
    error                = UnswError,
    outline              = UnswLightGrey,
)

// ─── Dark Colour Scheme ─────────────────────────────────────────────────────
private val DarkColorScheme = darkColorScheme(
    primary              = UnswYellow,
    onPrimary            = UnswNavy,
    primaryContainer     = UnswNavyDark,
    onPrimaryContainer   = UnswYellow,
    secondary            = UnswYellowLight,
    onSecondary          = UnswNavyDark,
    background           = Color(0xFF121212),
    onBackground         = UnswOffWhite,
    surface              = Color(0xFF1E1E1E),
    onSurface            = UnswOffWhite,
    surfaceVariant       = Color(0xFF2A2A2A),
    onSurfaceVariant     = UnswLightGrey,
    error                = Color(0xFFCF6679),
    outline              = Color(0xFF444444),
)

// ─── Typography ─────────────────────────────────────────────────────────────
val UnswTypography = Typography(
    displayLarge = TextStyle(
        fontWeight    = FontWeight.Bold,
        fontSize      = 36.sp,
        lineHeight    = 44.sp,
        letterSpacing = (-0.5).sp,
    ),
    displayMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize   = 28.sp,
        lineHeight = 36.sp,
    ),
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize   = 24.sp,
        lineHeight = 32.sp,
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize   = 20.sp,
        lineHeight = 28.sp,
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize   = 18.sp,
        lineHeight = 26.sp,
    ),
    titleMedium = TextStyle(
        fontWeight    = FontWeight.SemiBold,
        fontSize      = 16.sp,
        lineHeight    = 24.sp,
        letterSpacing = 0.1.sp,
    ),
    titleSmall = TextStyle(
        fontWeight    = FontWeight.Medium,
        fontSize      = 14.sp,
        lineHeight    = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize   = 16.sp,
        lineHeight = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize   = 14.sp,
        lineHeight = 20.sp,
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize   = 12.sp,
        lineHeight = 16.sp,
    ),
    labelLarge = TextStyle(
        fontWeight    = FontWeight.SemiBold,
        fontSize      = 14.sp,
        lineHeight    = 20.sp,
        letterSpacing = 0.5.sp,
    ),
    labelMedium = TextStyle(
        fontWeight    = FontWeight.Medium,
        fontSize      = 12.sp,
        lineHeight    = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
        fontWeight    = FontWeight.Medium,
        fontSize      = 10.sp,
        lineHeight    = 14.sp,
        letterSpacing = 0.5.sp,
    ),
)

// ─── App Theme ──────────────────────────────────────────────────────────────
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = UnswTypography,
        content     = content,
    )
}
