package com.example.studentsquadspb.android.data.model

data class MemberSquad(
    val idMember: Int,
    val idSquad: Int,
    val dateJoininig: String,
    val type: SquadMemberType,
    val positionIds: List<Int> = emptyList(),
)