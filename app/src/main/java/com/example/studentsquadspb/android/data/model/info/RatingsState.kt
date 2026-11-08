package com.example.studentsquadspb.android.data.model.info

data class RatingsState(
    val ratings: Map<Int, Float> = emptyMap(),
    val loading: Boolean = false,
    val lastRun: String? = null
)