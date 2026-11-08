package com.example.studentsquadspb.android.data.model.info

import androidx.annotation.DrawableRes

data class HeadquartersInfo (
    val id: String,
    @DrawableRes val emblem: Int,
    val name: String,
    val dateBirthday: String,
)