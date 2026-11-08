package com.example.studentsquadspb.android.data.remote

import kotlinx.coroutines.delay
import javax.inject.Inject

interface RatingApi {
    suspend fun getRating(id: Int): Float
}

class FakeRatingApi @Inject constructor() : RatingApi {
    override suspend fun getRating(id: Int): Float {
        delay(1500)                       // эмуляция сети
        return (30..50).random() / 10f
    }
}