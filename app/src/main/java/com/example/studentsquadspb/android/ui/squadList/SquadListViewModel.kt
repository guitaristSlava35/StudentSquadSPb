package com.example.studentsquadspb.android.ui.squadList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentsquadspb.android.data.remote.SquadListRepository
import com.example.studentsquadspb.android.data.model.info.SquadInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SquadListUiState {
    data object Loading : SquadListUiState
    data class Success(val squads: List<SquadInfo>) : SquadListUiState
    data class Error(val message: String) : SquadListUiState
}
@HiltViewModel
class SquadListViewModel @Inject constructor(
    private val repository: SquadListRepository,
): ViewModel() {

    private val _state = MutableStateFlow<SquadListUiState>(SquadListUiState.Loading)
    val state: StateFlow<SquadListUiState> = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _state.value = SquadListUiState.Loading
            repository.getSquads()
                .onSuccess { _state.value = SquadListUiState.Success(it) }
                .onFailure { _state.value = SquadListUiState.Error(it.message ?: "Ошибка загрузки")}
        }
    }
}
