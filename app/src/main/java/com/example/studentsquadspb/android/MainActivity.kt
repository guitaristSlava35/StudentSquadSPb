package com.example.studentsquadspb.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.studentsquadspb.android.data.SquadRepository
import com.example.studentsquadspb.android.navigation.AppNavigation
import com.example.studentsquadspb.android.ui.squadList.SquadListScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
@AndroidEntryPoint
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

//json-server --watch db.json --host 0.0.0.0 --port 3000
//& "C:\Users\gitar\AppData\Local\Android\Sdk\platform-tools\adb.exe" reverse tcp:3000 tcp:3000

