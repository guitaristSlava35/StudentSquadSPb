package com.example.studentsquadspb.android.data.remote.dto

import kotlinx.serialization.Serializable
@Serializable
data class MemberSquadDto(
    val squadId: Int,
    val year: String,
    val type: String
)

@Serializable
data class ExPositionDto(
    val id: Int,
    val title: String,
    val category: String,
    val year: Int
)

@Serializable
data class ProfileDto(
    val id: Int,
    val photoKey: String? = null,
    val surname: String,
    val name: String,
    val patronymic: String = "",
    val dateBirthday: String,
    val memberSquads: List<MemberSquadDto> = emptyList(),
    val fighterSquads: List<SquadDto> = emptyList(),
    val headquarters: List<String> = emptyList(),
    val directions: List<String> = emptyList(),
    val positions: List<PositionDto> = emptyList(),
    val exPositions: List<ExPositionDto> = emptyList(),
    val seasons: List<Int> = emptyList()
)