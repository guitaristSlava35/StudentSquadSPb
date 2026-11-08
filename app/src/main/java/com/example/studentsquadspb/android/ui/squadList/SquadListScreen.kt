package com.example.studentsquadspb.android.ui.squadList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentsquadspb.R
import com.example.studentsquadspb.android.data.SquadRepositoryImpl
import com.example.studentsquadspb.android.data.model.info.RatingsState
import com.example.studentsquadspb.android.data.model.info.SquadInfo


// ---------- Экран: тулбар + список ----------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SquadListScreen(
    squads: List<SquadInfo>,
    onAppClick: (SquadInfo) -> Unit,
    ratings: RatingsState = RatingsState(),
    onLoadSequential: () -> Unit = {},
    onLoadParallel: () -> Unit = {},
) {
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
        SquadList(
            squads = squads,
            onAppClick = onAppClick,
            ratings = ratings,
            onLoadSequential = onLoadSequential,
            onLoadParallel = onLoadParallel,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun SquadList(
    squads: List<SquadInfo>,
    onAppClick: (SquadInfo) -> Unit,
    ratings: RatingsState = RatingsState(),
    onLoadSequential: () -> Unit = {},
    onLoadParallel: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {                                   // ← шапка с кнопками
            Column {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = onLoadSequential, enabled = !ratings.loading) { Text("По очереди") }
                    Button(onClick = onLoadParallel, enabled = !ratings.loading) { Text("Параллельно") }
                }
                if (ratings.loading) {
                    LinearProgressIndicator(Modifier.fillMaxWidth().padding(top = 8.dp))
                }
                ratings.lastRun?.let { Text(it, Modifier.padding(top = 8.dp)) }
            }
        }
        items(squads) { squad ->
            SquadListItem(
                squad = squad,
                rating = ratings.ratings[squad.id],
                loading = ratings.loading,
                onClick = { onAppClick(squad) }
            )
        }    }
}

@Preview(showBackground = true)
@Composable
fun SquadListScreenPreview() {
    _root_ide_package_.com.example.studentsquadspb.android.AppListTheme {
        val repo = SquadRepositoryImpl()
        repo.getSquads()

        SquadListScreen(
            squads = repo.getSquads(),
            onAppClick = {}
        )
    }
}
