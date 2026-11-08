package com.example.studentsquadspb.android.data.remote

import com.example.studentsquadspb.android.data.model.SquadMember
import com.example.studentsquadspb.android.data.model.info.SquadInfo
import jakarta.inject.Inject

data class SquadDetail(
    val squad: SquadInfo,
    val members: List<SquadMember>
)

interface SquadDetailRepository {
    suspend fun getSquadDetail(squadId: Int): Result<SquadDetail>
}

class SquadDetailRepositoryImpl @Inject constructor(
    private val api: SquadApi
) : SquadDetailRepository {

    override suspend fun getSquadDetail(squadId: Int): Result<SquadDetail> = runCatching {
        SquadDetail(
            squad = api.getSquads(squadId).toDomain(),
            members = api.getSquadMembers(squadId).map { it.toDomain() }
        )

    }
}