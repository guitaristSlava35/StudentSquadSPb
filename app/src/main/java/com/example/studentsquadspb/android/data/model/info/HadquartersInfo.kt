package com.example.studentsquadspb.android.data.model.info

import androidx.annotation.DrawableRes

data class HadquartersInfo (
    val id: Int,
    @DrawableRes val emblem: Int,
    val name: String,
    val dateBirthday: String,
)