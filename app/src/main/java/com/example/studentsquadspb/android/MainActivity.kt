package com.example.studentsquadspb.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.studentsquadspb.android.navigation.AppNavigation
import com.example.studentsquadspb.android.ui.squadList.SquadListScreen

private val GreenColors = lightColorScheme(
    primary = Color(0xFF3D6B3F),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF3D6B3F),
    onPrimaryContainer = Color.White,
    surface = Color(0xFFE8EFE6),
    onSurface = Color(0xFF1B1B1B)
)

@Composable
fun AppListTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = GreenColors, content = content)
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppListTheme {
                AppNavigation()
            }
        }
    }
}

