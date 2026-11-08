package com.example.studentsquadspb.android.data.model

data class MemberHeadquarters (
    val idMember: Int,
    val idHadquarters: String,
    val dateJoining: String,
    val positionIds: List<Int> = emptyList(),
    val exPositionIds: List<Int> = emptyList()
)