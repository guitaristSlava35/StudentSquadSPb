package com.example.studentsquadspb.android.data.model

import androidx.annotation.DrawableRes
import com.example.studentsquadspb.android.data.model.position.Position

data class HeadquartersMember(
    val memberid: Int,
    @DrawableRes val photo: Int? = null,
    val fullname: String,
    val dateJoining: String,
    val positionHeadquarters: List<Position>

)