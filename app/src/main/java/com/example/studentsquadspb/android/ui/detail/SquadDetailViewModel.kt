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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update

sealed interface SquadDetailState {
    data object Loading : SquadDetailState
    data class Success(
        val detail: SquadDetail,
        val joinState: JoinState = JoinState.NotSent
    ) : SquadDetailState
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

    private var joinJob: Job? = null

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

    fun onJoinClick() {
        val current = _state.value
        if (current !is SquadDetailState.Success) return

        when (current.joinState) {
            is JoinState.Sending -> {
                joinJob?.cancel()
                joinJob = null
                setJoinState(JoinState.NotSent)
            }
            is JoinState.NotSent -> {
                joinJob = viewModelScope.launch { emulateSend() }
            }
            is JoinState.Sent -> Unit      // повторно не отправляем
        }
    }

    private suspend fun emulateSend() {
        for (progress in 0..100 step 5) {
            setJoinState(JoinState.Sending(progress))
            delay(150)
        }
        setJoinState(JoinState.Sent)
    }

    private fun setJoinState(newState: JoinState) {
        _state.update { s ->
            if (s is SquadDetailState.Success) s.copy(joinState = newState) else s
        }
    }
}