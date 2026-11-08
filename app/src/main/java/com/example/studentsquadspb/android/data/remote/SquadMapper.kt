package com.example.studentsquadspb.android.data.remote

import androidx.annotation.DrawableRes
import com.example.studentsquadspb.R
import com.example.studentsquadspb.android.data.model.MemberSquad
import com.example.studentsquadspb.android.data.model.MemberUiState
import com.example.studentsquadspb.android.data.model.SquadMember
import com.example.studentsquadspb.android.data.model.SquadMemberType
import com.example.studentsquadspb.android.data.model.info.MemberInfo
import com.example.studentsquadspb.android.data.model.info.SquadInfo
import com.example.studentsquadspb.android.data.model.position.ExPosition
import com.example.studentsquadspb.android.data.model.position.ExPositionCategory
import com.example.studentsquadspb.android.data.model.position.Position
import com.example.studentsquadspb.android.data.model.position.PositionCategory
import com.example.studentsquadspb.android.data.model.position.ProfileExPosition
import com.example.studentsquadspb.android.data.remote.dto.ExPositionDto
import com.example.studentsquadspb.android.data.remote.dto.PositionDto
import com.example.studentsquadspb.android.data.remote.dto.ProfileDto
import com.example.studentsquadspb.android.data.remote.dto.SquadDto
import com.example.studentsquadspb.android.data.remote.dto.SquadMemberDto
import com.example.studentsquadspb.android.ui.member.SquadEmblem

fun SquadDto.toDomain() = SquadInfo(
    id = id,
    emblem = emblemRes(emblemKey),
    name = name,
    dateBirthday = dateBirthday,
    directionWork = directionWork,
    headquarters = headquarters
)

fun PositionDto.toDomain() = Position(
    id = id,
    title = title,
    category = runCatching { PositionCategory.valueOf(category) }
        .getOrDefault(PositionCategory.OTHER)
)

fun SquadMemberDto.toDomain() = SquadMember(
    memberId = memberId,
    photo = photoRes(photoKey),
    fullName = fullName,
    dateJoinig = year,
    type = SquadMemberType.valueOf(type),
    positionsSquad = positions.map { it.toDomain() }
)

fun ExPositionDto.toDomain() = ProfileExPosition(
    year = year,
    exPosition = ExPosition(
        id = id,
        title = title,
        exCategory = runCatching { ExPositionCategory.valueOf(category) }
            .getOrDefault(ExPositionCategory.EX_COMMAND_SQUAD)
    )
)

fun ProfileDto.toDomain(squad: SquadInfo) = MemberUiState(
    squad = squad,
    member = MemberInfo(
        id = id,
        photo = photoRes(photoKey),
        surname = surname,
        name = name,
        patronymic = patronymic,
        dateBirthday = dateBirthday
    ),
    memberSquads = memberSquads.map {
        MemberSquad(
            idMember = id,
            idSquad = it.squadId,
            dateJoinig = it.year,
            type = SquadMemberType.valueOf(it.type)
        )
    },
    squads = fighterSquads.map { it.toDomain() },
    headquarters = headquarters,
    directions = directions,
    positions = positions.map { it.toDomain() },
    exPositions = exPositions.map { it.toDomain() },
    seasons = seasons
)
@DrawableRes
fun emblemRes(key: String): Int = when (key) {
    "altair" -> R.drawable.ic_altair
    "artifex" -> R.drawable.ic_artifex
    "django" -> R.drawable.ic_django
    "legenda" -> R.drawable.ic_legenda
    "los" -> R.drawable.ic_los
    "plamya" -> R.drawable.ic_plamya
    "provorniy" -> R.drawable.ic_provorniy
    "svarog" -> R.drawable.ic_svarog
    "orion" -> R.drawable.ic_orion
    else -> R.drawable.ic_spbso
}
fun photoRes(key: String?): Int? = when (key) {
    "spbso" -> R.drawable.ic_logo_spbso
    else -> null
}