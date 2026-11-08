package com.example.studentsquadspb.android.ui.squadList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentsquadspb.android.data.model.info.RatingsState
import com.example.studentsquadspb.android.data.remote.SquadListRepository
import com.example.studentsquadspb.android.data.model.info.SquadInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

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

    private val _ratings = MutableStateFlow(RatingsState())
    val ratings: StateFlow<RatingsState> = _ratings.asStateFlow()
    private var ratingsJob: Job? = null

    fun loadRatings(parallel: Boolean) {
        val s = _state.value as? SquadListUiState.Success ?: return
        val ids = s.squads.map { it.id }
        ratingsJob?.cancel()
        ratingsJob = viewModelScope.launch {
            _ratings.value = RatingsState(loading = true)
            var result: Map<Int, Float> = emptyMap()
            val time = measureTimeMillis {
                result = if (parallel) repository.loadRatingsInParallel(ids)
                else repository.loadRatingsSequentially(ids)
            }
            val mode = if (parallel) "Параллельно" else "Последовательно"
            Log.d("RATING", "$mode: ${ids.size} шт., $time мс")
            _ratings.value = RatingsState(result, false, "$mode: $time мс")
        }
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
