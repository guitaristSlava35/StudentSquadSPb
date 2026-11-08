package com.example.studentsquadspb.android.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentsquadspb.android.data.remote.SquadDetail
import com.example.studentsquadspb.android.data.remote.SquadDetailRepository
import com.example.studentsquadspb.android.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SquadDetailState {
    data object Loading : SquadDetailState
    data class Success(val detail: SquadDetail) : SquadDetailState
    data class Error(val message: String) : SquadDetailState
}

@HiltViewModel
class SquadDetailViewModel @Inject constructor(
    private val repository: SquadDetailRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val squadId: Int = checkNotNull(savedStateHandle.get<Int>(Routes.ARG_SQUAD_ID))

    private val _state = MutableStateFlow<SquadDetailState>(SquadDetailState.Loading)
    val state: StateFlow<SquadDetailState> = _state.asStateFlow()

    init { load() }

    fun load() {
        viewModelScope.launch {
            _state.value = SquadDetailState.Loading
            repository.getSquadDetail(squadId)
                .onSuccess { _state.value = SquadDetailState.Success(it) }
                .onFailure { _state.value = SquadDetailState.Error(it.message ?: "Ошибка загрузки") }
                .onFailure { e ->
                    val text = (e as? retrofit2.HttpException)?.let { "Ошибка ${it.code()}: ${it.response()?.raw()?.request?.url}" }
                        ?: (e.message ?: "Ошибка загрузки")
                    _state.value = SquadDetailState.Error(text)
                }
        }
    }
}