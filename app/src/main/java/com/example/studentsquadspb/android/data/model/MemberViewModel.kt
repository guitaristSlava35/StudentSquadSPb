package com.example.studentsquadspb.android.data.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.studentsquadspb.android.data.SquadRepository
import com.example.studentsquadspb.android.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val repository: SquadRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val squadId: Int = checkNotNull(savedStateHandle[Routes.ARG_SQUAD_ID])
    private val memberId: Int = checkNotNull(savedStateHandle[Routes.ARG_MEMBER_ID])

    private val _uiState = MutableStateFlow(load())
    val uiState: StateFlow<MemberUiState?> = _uiState.asStateFlow()

    private fun load(): MemberUiState? {
        val squad = repository.getSquad(squadId) ?: return null
        val member = repository.getMember(memberId) ?: return null

        return MemberUiState(
            squad = squad,
            member = member,
            memberSquads = repository.getMemberSquads(memberId),
            squads = repository.getFighterSquads(memberId),
            headquarters = repository.getProfileHadquarters(memberId),
            directions = repository.getProfileDirectionWork(memberId),
            positions = repository.getProfileRoleSquad(memberId),
            exPositions = repository.getProfileExRoleSquad(memberId),
            seasons = repository.getMemberSeasons(memberId)
        )
    }

}