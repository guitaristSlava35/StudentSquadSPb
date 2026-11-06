package com.example.studentsquadspb.android.data.model

enum class SquadMemberType(val title: String){
    COMMAND("Командный состав"),
    FIGHTER("Бойцы"),
    CANDIDATE("Кандидаты");

    val isFighter: Boolean
        get() = this == FIGHTER || this == COMMAND
}