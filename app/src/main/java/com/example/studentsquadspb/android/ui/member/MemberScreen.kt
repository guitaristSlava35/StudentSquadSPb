package com.example.studentsquadspb.android.ui.member

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import com.example.studentsquadspb.R
import com.example.studentsquadspb.android.AppListTheme
import com.example.studentsquadspb.android.data.FakeSquadRepository
import com.example.studentsquadspb.android.data.directionLogo
import com.example.studentsquadspb.android.data.exPositionLogo
import com.example.studentsquadspb.android.data.headquartersLogo
import com.example.studentsquadspb.android.data.model.info.MemberInfo
import com.example.studentsquadspb.android.data.model.MemberSquad
import com.example.studentsquadspb.android.data.model.SquadMember
import com.example.studentsquadspb.android.data.model.position.PositionCategory
import com.example.studentsquadspb.android.data.model.position.Position
import com.example.studentsquadspb.android.data.model.info.SquadInfo
import com.example.studentsquadspb.android.data.model.position.ExPositionCategory
import com.example.studentsquadspb.android.data.model.position.ProfileExPosition
import com.example.studentsquadspb.android.data.positionLogo
import com.example.studentsquadspb.android.ui.detail.InfoField
import com.example.studentsquadspb.android.ui.theme.directionColor

private val photoProfile = 102.dp

@Composable
fun PositionColumn(
    positions: List<Position>,
    modifier: Modifier = Modifier
) {
    if (positions.isEmpty()) return   // пустая категория ничего не рисует

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        positions.forEach { p ->
            positionLogo(p.id)?.let { PatchImage(it, p.title) }
        }
    }
}

@Composable
fun ExPositionRow(
    items: List<ProfileExPosition>,
    modifier: Modifier = Modifier
) {
    if (items.isEmpty()) return

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            exPositionLogo(item.exPosition.id)?.let { icon ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.padding(horizontal = 4.dp)
                ){
                    Text(
                        text = item.year.toString(),
                        style = MaterialTheme.typography.labelSmall,
                        modifier = modifier.padding(horizontal = 4.dp)
                    )
                    PatchImage(icon, item.exPosition.title, Modifier.width(100.dp))

                }
            }
        }
    }
}
@Composable
fun PatchImage(
    @DrawableRes icon: Int,
    name: String,
    modifier: Modifier = Modifier
){
    Image(
        painter = painterResource(icon),
        contentDescription = name,
        modifier = modifier
            .width(160.dp),

        contentScale = ContentScale.Fit
    )
}


@Composable
fun InitialsMember(
    surname: String,
    name: String,
    modifier: Modifier = Modifier
){
    val initials = listOf(surname, name)
        .filter { it.isNotBlank() }
        .joinToString(""){it.first().uppercase()}

    Box(
        modifier = Modifier
            .size(photoProfile)
            .clip(CircleShape)
            .background(Color(0xFFE8EFE6)),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = initials,
            color = Color(0xFF1B1B1B),
            fontWeight = FontWeight.Medium
        )

    }
}

@Composable
fun MemberPhoto (
    name: String,
    surname: String,
    @DrawableRes photo: Int?,
    modifier: Modifier = Modifier
){
    if(photo != null)
        Image(
            painter = painterResource(photo),
            contentDescription = name,
            modifier = Modifier
                .size(photoProfile)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    else
        InitialsMember(surname, name, modifier)
}


@Composable
fun MemberScreen(
    squad: SquadInfo,
    member: MemberInfo,
    membersquad: List<MemberSquad>,
    onBack: () -> Unit,
    onSquadClick: (SquadInfo) -> Unit,
    modifier: Modifier = Modifier
){
    val squads = FakeSquadRepository.getFighterSquads(member.id)
    val headquarters = FakeSquadRepository.getProfileHadquarters(member.id)
    val directions = FakeSquadRepository.getProfileDirectionWork(member.id)
    val positions = FakeSquadRepository.getProfileRoleSquad(member.id)
    val seasons = FakeSquadRepository.getMemberSeasons(member.id)
    val exPositions = FakeSquadRepository.getProfileExRoleSquad(member.id)

    val exGrouped = exPositions.groupBy { it.exPosition.exCategory }
    val grouped = positions.groupBy {it.category}
    val isFighter = membersquad.any { it.type.isFighter }

    val commandPositions = grouped[PositionCategory.COMMAND_SQUAD].orEmpty()
    val flagPositions = grouped[PositionCategory.FLAG].orEmpty()
    val otherPositions = grouped[PositionCategory.OTHER].orEmpty()
    val exCommand = exGrouped[ExPositionCategory.EX_COMMAND_SQUAD].orEmpty()



    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = directionColor(squad.directionWork),
        contentColor = Color.White
    ) {
        LazyColumn(
            modifier = Modifier.statusBarsPadding(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            item{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.15f))
                    ){
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }

                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp)
                        .height(IntrinsicSize.Min),
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row() {
                            MemberPhoto(name = member.name, surname = member.surname, photo = member.photo)

                            Column(
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                InfoField("ФИО", listOf(member.surname, member.name)
                                    .filter { it.isNotBlank() }
                                    .joinToString(" "))
                                Spacer(Modifier.padding(4.dp))
                                InfoField("День рождения", member.dateBirthday)
                            }

                        }
                        HorizontalDivider(
                            modifier = Modifier
                                .padding(vertical = 6.dp),
                            color = Color.White.copy(alpha = 0.7f)

                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        ) {

                        }
                        if (isFighter){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ){
                                    seasons.forEach { year ->

                                        if ((year - 1948)%5==0){
                                            Image(
                                                painter = painterResource(R.drawable.ic_blue_kirpich),
                                                contentDescription = "Blue_kirpich",
                                                modifier = Modifier.width(56.dp),
                                                contentScale = ContentScale.Fit
                                            )
                                        }
                                        else{
                                            Image(
                                                painter = painterResource(R.drawable.ic_red_kirpich),
                                                contentDescription = "Red_kirpich",
                                                modifier = Modifier.width(56.dp),
                                                contentScale = ContentScale.Fit
                                            )
                                        }
                                        Text(year.toString())
                                    }

                                }

                                Column(
                                    modifier = Modifier.width(300.dp),
                                    horizontalAlignment = Alignment.End,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ){

                                    Image(
                                        painter = painterResource(R.drawable.ic_spbso),
                                        contentDescription = "СПбСО",
                                        modifier = Modifier.width(160.dp),
                                        contentScale = ContentScale.Fit
                                    )

                                    headquarters.forEach { hq ->
                                        PatchImage(headquartersLogo(hq), hq)
                                    }

                                    directions.forEach { dir ->
                                        PatchImage(directionLogo(dir), dir)
                                    }

                                    PositionColumn(commandPositions)

                                    PositionColumn(flagPositions)

                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp),
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        otherPositions.forEach { p ->
                                            positionLogo(p.id)?.let { PatchImage(it, p.title, Modifier.width(70.dp).padding(horizontal = 4.dp)) }
                                        }
                                    }
                                    ExPositionRow(exGrouped[ExPositionCategory.EX_FLAG].orEmpty())
                                    ExPositionRow(exGrouped[ExPositionCategory.EX_COMMAND_SQUAD].orEmpty())

                                }

                            }
                        }

                    }
                    VerticalDivider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(horizontal = 2.dp),
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Column(
                        modifier = Modifier
                            .padding(2.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Text("Отряды")

                        squads.forEach { fighterSquad ->
                            SquadListProfileItem(fighterSquad, onClick = {onSquadClick(fighterSquad)})
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MemberScreenPriview(){
    val member = FakeSquadRepository.getMembers(1).first()

    AppListTheme {
        MemberScreen(
            squad = FakeSquadRepository.getSquad(1)!!,
            member = FakeSquadRepository.getMember(6)!!,
            membersquad = FakeSquadRepository.getMemberSquads(1),
            onBack = {},
            onSquadClick = {}
        )
    }

}