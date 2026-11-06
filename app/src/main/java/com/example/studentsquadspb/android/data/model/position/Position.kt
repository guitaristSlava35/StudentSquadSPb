package com.example.studentsquadspb.android.data.model.position

data class Position(
    val id: Int,
    val title: String,
    val category: PositionCategory = PositionCategory.OTHER
)