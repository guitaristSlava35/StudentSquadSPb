package com.example.studentsquadspb.android.data.remote

import com.example.studentsquadspb.android.data.model.info.SquadInfo
import jakarta.inject.Inject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

interface SquadListRepository {
    suspend fun getSquads(): Result<List<SquadInfo>>
    suspend fun loadRatingsSequentially(ids: List<Int>): Map<Int, Float>
    suspend fun loadRatingsInParallel(ids: List<Int>): Map<Int, Float>
}

class SquadListRepositoryImpl @Inject constructor(
    private val api: SquadApi,
    private val ratingApi: RatingApi
) : SquadListRepository {
    override suspend fun getSquads(): Result<List<SquadInfo>> =
        runCatching { api.getSquads().map {it.toDomain()} }

    override suspend fun loadRatingsSequentially(ids: List<Int>): Map<Int, Float> {
        val result = mutableMapOf<Int, Float>()
        for (id in ids) result[id] = ratingApi.getRating(id)   // по очереди
        return result
    }

    override suspend fun loadRatingsInParallel(ids: List<Int>): Map<Int, Float> = coroutineScope {
        ids.map { id -> async { id to ratingApi.getRating(id) } }
            .awaitAll()
            .toMap()
    }
}

