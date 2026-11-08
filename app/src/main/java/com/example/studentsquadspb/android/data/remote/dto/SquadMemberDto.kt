package com.example.studentsquadspb.android.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PositionDto (
    val id: Int,
    val title: String,
    val category: String
)

@Serializable
data class SquadMemberDto(
    val id: Int,
    val squadId: Int,
    val memberId: Int,
    val fullName: String,
    val photoKey: String? = null,
    val year: String,
    val type: String,
    val positions: List<PositionDto> = emptyList()
)