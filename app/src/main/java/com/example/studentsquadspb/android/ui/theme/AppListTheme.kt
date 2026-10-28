package com.example.studentsquadspb.android.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color

private val GreenColors = lightColorScheme(
    primary = Color(0xFF3D6B3F),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF3D6B3F),
    onPrimaryContainer = Color.White,
    background = Color(0xFFE8EFE6),
    surface = Color(0xFFE8EFE6),
    onSurface = Color(0xFF1B1B1B)
)

fun directionColor(direction: String): Color = when (direction) {
    "СОП" -> Color(0xFF2F5C8A)
    else -> Color(0xFF3D6B3F)
}

@Composable
fun AppListTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = GreenColors, content = content)
}