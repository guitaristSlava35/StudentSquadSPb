package com.example.studentsquadspb.android.ui.headquartersList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.studentsquadspb.android.data.model.info.HeadquartersInfo
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.studentsquadspb.R
import com.example.studentsquadspb.android.data.SquadRepositoryImpl


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HadquartersListScreen (
    headquarters: List<HeadquartersInfo>
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Штабы Спб") },
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
    ) {
        padding ->
        HadquartersList(
            headquarters = headquarters,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun HadquartersList(
    headquarters: List<HeadquartersInfo>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(headquarters){
            headquarters -> HeadquartersListItem(headquarters = headquarters)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HadquartersListScreenPreview(){
    _root_ide_package_.com.example.studentsquadspb.android.AppListTheme{
        val repo = SquadRepositoryImpl()
        repo.getHeadquarters()

        HadquartersListScreen(
            headquarters = repo.getHeadquarters()
        )
    }
}


