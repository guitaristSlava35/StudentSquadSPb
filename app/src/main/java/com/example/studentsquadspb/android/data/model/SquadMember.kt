package com.example.studentsquadspb.android.data.model

import androidx.annotation.DrawableRes

/** То, что показывает экран: человек вместе с его ролью в конкретном отряде. */
data class SquadMember(
    val memberId: Int,
    @DrawableRes val photo: Int ?= null,
    val fullName: String,
    val dateJoininig: String,
    val type: SquadMemberType,
    val positions: List<Position>
)