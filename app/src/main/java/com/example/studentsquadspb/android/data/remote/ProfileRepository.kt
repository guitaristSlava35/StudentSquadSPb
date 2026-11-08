package com.example.studentsquadspb.android.data.remote

import com.example.studentsquadspb.android.data.model.MemberUiState
import jakarta.inject.Inject

interface ProfileRepository {
    suspend fun getProfile(squadId: Int, memberId: Int): Result<MemberUiState>
}

class ProfileRepositoryImpl @Inject constructor(
    private val api: SquadApi
) : ProfileRepository {

    override suspend fun getProfile(squadId: Int, memberId: Int): Result<MemberUiState> = runCatching {
        val squad = api.getSquads(squadId).toDomain()
        api.getProfile(memberId).toDomain(squad)
    }
}