package com.example.studentsquadspb.android.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon

import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentsquadspb.android.data.SquadRepositoryImpl
import com.example.studentsquadspb.android.data.directionLogo
import com.example.studentsquadspb.android.data.headquartersLogo
import com.example.studentsquadspb.android.data.model.info.SquadInfo
import com.example.studentsquadspb.android.data.model.SquadMember
import com.example.studentsquadspb.android.data.model.SquadMemberType
import com.example.studentsquadspb.android.ui.theme.AppListTheme
import com.example.studentsquadspb.android.ui.theme.directionColor
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset

@Composable
fun SquadDetailScreen(
    squad: SquadInfo,
    members: List<SquadMember>,
    onBack: () -> Unit,
    onMemberClick: (SquadMember) -> Unit
){
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val tabs = SquadMemberType.entries
    val shownMembers = members.filter { it.type == tabs[selectedTab] }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = directionColor(squad.directionWork),
        contentColor = Color.White,
    ) {
        LazyColumn(
            modifier = Modifier.statusBarsPadding(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            item {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.15f))

                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Назад"
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(squad.emblem),
                        contentDescription = squad.name,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(124.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(16.dp))
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(end = 16.dp)
                    ){
                        InfoField("Название", squad.name)
                        InfoField("Дата основания", squad.dateBirthday.ifBlank { "—" })
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text("Направление", style = MaterialTheme.typography.labelSmall)
                        Spacer(Modifier.height(6.dp))
                        Image(
                            painter = painterResource(directionLogo(squad.directionWork)),
                            contentDescription = squad.directionWork,
                            modifier = Modifier.width(160.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Штаб", style = MaterialTheme.typography.labelSmall)
                        Spacer(Modifier.height(6.dp))
                        Image(
                            painter = painterResource(headquartersLogo(squad.headquarters)),
                            contentDescription = squad.headquarters,
                            modifier = Modifier.width(160.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }

            item {
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            height = 3.dp,
                            color = Color.White
                        )

                    }
                ) {
                    tabs.forEachIndexed { index, type ->
                        val isSelected = selectedTab == index

                        Tab(
                            selected = isSelected,
                            modifier = Modifier.background(
                                if(isSelected) Color.Black.copy(alpha = 0.15f) else Color.Transparent
                            ),
                            onClick = { selectedTab = index },
                            text = { Text(type.title, textAlign = TextAlign.Center) }
                        )
                    }
                }
            }

            if (shownMembers.isEmpty()){
                item {
                    Text(
                        text = "Пока никого нет",
                        modifier = Modifier.padding(vertical = 24.dp, horizontal = 8.dp)
                    )
                }
            }
            else {
                items(shownMembers, key = { it.memberId }) {
                    member -> SquadItem(member, onClick = { onMemberClick(member) })
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SquadDetailsScreenPreview() {
    val repo = SquadRepositoryImpl()
    val squad = repo.getSquads().first()


    AppListTheme {
        SquadDetailScreen(
            squad = squad,
            members = repo.getMembers(squad.id),
            onBack = {},
            onMemberClick = {}
        )
    }
}

@Preview(name = "Без Участников", showBackground = true, showSystemUi = true)
@Composable
fun SquadDetailsScreenEmptyPreview() {
    val repo = SquadRepositoryImpl()
    val squad = repo.getSquads().first()

    AppListTheme {
        SquadDetailScreen(
            squad = squad,
            members = emptyList(),
            onBack = {},
            onMemberClick = {}
        )
    }
}

@Preview(name = "Проводники", showBackground = true, showSystemUi = true)
@Composable
fun SquadDetailsScreenSOPPreview() {
    val repo = SquadRepositoryImpl()
    val squad = repo.getSquads().first { it.name == "Проворный" }

    AppListTheme {
        SquadDetailScreen(
            squad = squad,
            members = repo.getMembers(squad.id),
            onBack = {},
            onMemberClick = {}
        )
    }
}