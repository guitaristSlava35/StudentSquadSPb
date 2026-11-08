package com.example.studentsquadspb.android.ui.detail

sealed interface JoinState {
    data object NotSent : JoinState
    data class Sending(val progress: Int) : JoinState
    data object Sent : JoinState
}