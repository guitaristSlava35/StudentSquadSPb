package com.example.studentsquadspb.android.data.remote

import com.example.studentsquadspb.android.data.model.info.SquadInfo
import jakarta.inject.Inject

interface SquadListRepository {
    suspend fun getSquads(): Result<List<SquadInfo>>
}

class SquadListRepositoryImpl @Inject constructor(
    private val api: SquadApi
) : SquadListRepository {
    override suspend fun getSquads(): Result<List<SquadInfo>> =
        runCatching { api.getSquads().map {it.toDomain()} }
}