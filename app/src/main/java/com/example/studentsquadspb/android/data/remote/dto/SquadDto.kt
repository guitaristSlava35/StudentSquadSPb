package com.example.studentsquadspb.android.data.remote.dto

import androidx.compose.ui.text.style.TextDirection
import kotlinx.serialization.Serializable

@Serializable
data class SquadDto (
    val id: Int,
    val emblemKey: String,
    val name: String,
    val dateBirthday: String,
    val directionWork: String,
    val headquarters: String
)