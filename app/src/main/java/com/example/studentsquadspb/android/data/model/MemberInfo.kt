package com.example.studentsquadspb.android.data.model

import androidx.annotation.DrawableRes

data class MemberInfo(
    val id: Int,
    @DrawableRes val photo: Int ?= null,
    val name: String,
    val surname: String,
    val patronymic: String ?= null,
    val dateBirthday: String,
)

