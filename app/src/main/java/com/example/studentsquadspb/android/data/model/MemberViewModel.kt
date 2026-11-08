package com.example.studentsquadspb.android.data.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentsquadspb.android.data.SquadRepository
import com.example.studentsquadspb.android.data.remote.ProfileRepository
import com.example.studentsquadspb.android.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface MemberLoadState {
    data object Loading : MemberLoadState
    data class Success(val ui: MemberUiState) : MemberLoadState
    data class Error(val message: String) : MemberLoadState
}

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val repository: ProfileRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val squadId: Int = checkNotNull(savedStateHandle.get<Int>(Routes.ARG_SQUAD_ID))
    private val memberId: Int = checkNotNull(savedStateHandle.get<Int>(Routes.ARG_MEMBER_ID))

    private val _state = MutableStateFlow<MemberLoadState>(MemberLoadState.Loading)
    val state: StateFlow<MemberLoadState> = _state.asStateFlow()

    init { load() }

    fun load() {
        viewModelScope.launch {
            _state.value = MemberLoadState.Loading
            repository.getProfile(squadId, memberId)
                .onSuccess { _state.value = MemberLoadState.Success(it) }
                .onFailure { _state.value = MemberLoadState.Error(it.message ?: "Ошибка загрузки") }
        }
    }
}