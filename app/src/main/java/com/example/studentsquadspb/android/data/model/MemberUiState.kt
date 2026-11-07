package com.example.studentsquadspb.android.data.model

import com.example.studentsquadspb.android.data.model.info.MemberInfo
import com.example.studentsquadspb.android.data.model.info.SquadInfo
import com.example.studentsquadspb.android.data.model.position.Position
import com.example.studentsquadspb.android.data.model.position.ProfileExPosition

data class MemberUiState(
    val squad: SquadInfo,
    val member: MemberInfo,
    val memberSquads: List<MemberSquad>,
    val squads: List<SquadInfo>,
    val headquarters: List<String>,
    val directions: List<String>,
    val positions: List<Position>,
    val exPositions: List<ProfileExPosition>,
    val seasons: List<Int>
    )