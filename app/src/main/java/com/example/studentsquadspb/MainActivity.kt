package com.example.studentsquadspb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat

// ---------- Данные ----------

data class SquadInfo(
    @DrawableRes val emblem: Int,
    val name: String,
    val direction_work: String
)

val squads = listOf(
    SquadInfo(R.drawable.ic_altair, "Альтаир", "ССервО"),
    SquadInfo(R.drawable.ic_artifex, "Артифекс", "САО"),
    SquadInfo(R.drawable.ic_django, "Джанго", "ССхО"),
    SquadInfo(R.drawable.ic_legenda, "Легенда", "СПО"),
    SquadInfo(R.drawable.ic_los, "Лось", "ССхО"),
    SquadInfo(R.drawable.ic_plamya, "Пламя", "ССО"),
    SquadInfo(R.drawable.ic_provorniy, "Проворный", "СОП"),
    SquadInfo(R.drawable.ic_svarog, "Сварог", "СПрО")
)

@DrawableRes
fun directionLogo(direction: String): Int {
    return when(direction){
        "ССО" -> R.drawable.ic_logo_sso
        "СПО" -> R.drawable.ic_logo_spo
        "ССхО" -> R.drawable.ic_logo_ssho
        "ССервО" -> R.drawable.ic_logo_sservo
        "САО" -> R.drawable.ic_logo_sao
        "СОП" -> R.drawable.ic_logo_sop
        else -> R.drawable.ic_logo_sso
    }
}



// ---------- Тема ----------

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

// ---------- Элемент списка ----------

@Composable
fun SquadListItem(squad: SquadInfo, modifier: Modifier = Modifier) {

    val cardColor = when (squad.direction_work){
        "СОП" -> Color(0xFF2F5C8A)
        else -> Color(0xFF3D6B3F)
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor,
            contentColor = Color.White,
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(squad.emblem),
                contentDescription = squad.name,
                modifier = Modifier
                    .padding(6.dp)
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column{
                Row {
                    Text(
                        text = squad.direction_work,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(2.dp)
                    )

                    Text(
                        text = squad.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(2.dp)
                    )}

                Image(
                    painter = painterResource(directionLogo(squad.direction_work)),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .width(96.dp),
                    contentScale = ContentScale.Fit
                )

            }

        }

    }
}

// ---------- Список ----------

@Composable
fun SquadList(squads: List<SquadInfo>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(squads) { squad -> SquadListItem(squad) }
    }
}

// ---------- Экран: тулбар + список ----------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SquadListScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Отряды Спб") },
                navigationIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_logo_spbso),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(32.dp),
                        tint = Color.Unspecified,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        },
    ) { padding ->
        SquadList(squads, modifier = Modifier.padding(padding))
    }
}

// ---------- Activity ----------

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppListTheme {
                SquadListScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SquadListScreenPreview() {
    AppListTheme {
        SquadListScreen()
    }
}