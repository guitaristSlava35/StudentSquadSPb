package com.example.studentsquadspb.android.data.model

import androidx.annotation.DrawableRes
import com.example.studentsquadspb.android.data.model.position.MemberExPosition
import com.example.studentsquadspb.android.data.model.position.Position

/** То, что показывает экран: человек вместе с его ролью в конкретном отряде. */
data class SquadMember(
    val memberId: Int,
    @DrawableRes val photo: Int ?= null,
    val fullName: String,
    val dateJoinig: String,
    val type: SquadMemberType,
    val positionsSquad: List<Position>,
)