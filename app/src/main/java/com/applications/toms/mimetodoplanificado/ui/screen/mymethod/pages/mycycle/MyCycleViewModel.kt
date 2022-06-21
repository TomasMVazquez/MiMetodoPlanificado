package com.applications.toms.mimetodoplanificado.ui.screen.mymethod.pages.mycycle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.domain.PainScaleModel
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.mimetodoplanificado.ui.utils.methods.TOTAL_CYCLE_DAYS
import com.applications.toms.usecases.cycle.DeleteCycleUseCase
import com.applications.toms.usecases.cycle.GetCycleUseCase
import com.applications.toms.usecases.cycle.SaveCycleUseCase
import com.applications.toms.usecases.painscale.SavePainScaleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MyCycleViewModel @Inject constructor(
    private val getCycleUseCase: GetCycleUseCase,
    private val saveCycleUseCase: SaveCycleUseCase,
    private val deleteCycleUseCase: DeleteCycleUseCase,
    private val savePainScaleUseCase: SavePainScaleUseCase
): ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

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
                    if (error != ErrorStates.NOT_FOUND) {
                        emitEffect(Effect.Error(error))
                        _state.update { state ->
                            state.copy(
                                loading = false,
                                hasCycleConfigured = false
                            )
                        }
                    }
                }
        }
    }

    fun saveMyCycle(localDate: LocalDate) {
        viewModelScope.launch {
            saveCycleUseCase.execute(
                SaveCycleUseCase.Input(
                    startDate = localDate,
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
                    emitEffect(Effect.Error(error))
                    _state.update { state ->
                        state.copy(
                            loading = false
                        )
                    }
                }
        }
    }

    fun onSavePainScale(painScale: Int) {
        viewModelScope.launch {
            savePainScaleUseCase.execute(
                PainScaleModel(
                    date = LocalDate.now(),
                    painScale = painScale
                )
            ).onSuccess {
                emitEffect(Effect.Success(SAVE_PAIN_SUCCESS))
            }.onFailure {
                emitEffect(Effect.Error(it))
            }
        }
    }

    private fun emitEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.send(
                effect
            )
        }
    }

    data class State(
        val loading: Boolean = true,
        val hasCycleConfigured: Boolean = false,
        val startDate: LocalDate? = LocalDate.now(),
        val endDate: LocalDate? = LocalDate.now(),
        val nextCycle: LocalDate? = null,
        val totalDaysCycle: Long = TOTAL_CYCLE_DAYS
    )

    sealed class Effect {
        data class Error(
            val error: ErrorStates
        ) : Effect()

        data class Success(
            val from: Int
        ) : Effect()
    }

    companion object {
        const val SAVE_PAIN_SUCCESS = 1
    }

}