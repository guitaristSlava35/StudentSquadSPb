package com.example.studentsquadspb.android.data.model

import androidx.annotation.DrawableRes
import com.example.studentsquadspb.R

data class SquadInfo(
    val id: Int,
    @DrawableRes val emblem: Int,
    val name: String,
    val dateBirthday: String,
    val directionWork: String,
    val headquarters: String,

)

