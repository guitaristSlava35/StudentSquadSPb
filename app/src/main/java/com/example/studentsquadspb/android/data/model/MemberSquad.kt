package com.example.studentsquadspb.android.data.model

import com.example.studentsquadspb.android.data.model.position.ExPosition

data class MemberSquad(
    val idMember: Int,
    val idSquad: Int,
    val dateJoininig: String,
    val type: SquadMemberType,
    val positionIds: List<Int> = emptyList(),
    val exPositionIds: List<Int> = emptyList()
)