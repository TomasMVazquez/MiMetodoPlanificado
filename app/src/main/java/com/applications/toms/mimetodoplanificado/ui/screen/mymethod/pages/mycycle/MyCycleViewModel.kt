package com.applications.toms.mimetodoplanificado.ui.screen.mymethod.pages.mycycle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.mimetodoplanificado.ui.utils.methods.TOTAL_CYCLE_DAYS
import com.applications.toms.usecases.cycle.DeleteCycleUseCase
import com.applications.toms.usecases.cycle.GetCycleUseCase
import com.applications.toms.usecases.cycle.SaveCycleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MyCycleViewModel @Inject constructor(
    private val getCycleUseCase: GetCycleUseCase,
    private val saveCycleUseCase: SaveCycleUseCase,
    private val deleteCycleUseCase: DeleteCycleUseCase
): ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    init {
        getCycleData()
    }

    private fun getCycleData() {
        viewModelScope.launch {
            getCycleUseCase.execute(Unit)
                .onSuccess { response ->
                    _state.value = State(
                        loading = false,
                        hasCycleConfigured = true,
                        startDate = response.startDate,
                        endDate = response.startDate.plusDays(response.totalDaysCycle - 1),
                        nextCycle = response.nextCycle,
                        totalDaysCycle = response.totalDaysCycle
                    )
                }
                .onFailure { error ->
                    _state.value = state.value.copy(
                        loading = false,
                        hasCycleConfigured = false,
                        errorState = error
                    )
                }
        }
    }

    fun saveMyCycle() {
        viewModelScope.launch {
            saveCycleUseCase.execute(
                SaveCycleUseCase.Input(
                    startDate = LocalDate.now(),
                    totalCycleDays = TOTAL_CYCLE_DAYS
                )
            )
                .onSuccess { response ->
                    _state.value = State(
                        loading = false,
                        hasCycleConfigured = true,
                        startDate = response.startDate,
                        endDate = response.startDate.plusDays(response.totalDaysCycle - 1),
                        nextCycle = response.nextCycle,
                        totalDaysCycle = response.totalDaysCycle
                    )
                }
                .onFailure { error ->
                    _state.value = state.value.copy(
                        loading = false,
                        errorState = error
                    )
                }
        }
    }

    fun onResetError() {
        _state.value = state.value.copy(
            errorState = null
        )
    }

    data class State(
        val loading: Boolean = true,
        val hasCycleConfigured: Boolean = false,
        val startDate: LocalDate? = LocalDate.now(),
        val endDate: LocalDate? = LocalDate.now(),
        val nextCycle: LocalDate? = null,
        val totalDaysCycle: Long = TOTAL_CYCLE_DAYS,
        val errorState: ErrorStates? = null
    )

}