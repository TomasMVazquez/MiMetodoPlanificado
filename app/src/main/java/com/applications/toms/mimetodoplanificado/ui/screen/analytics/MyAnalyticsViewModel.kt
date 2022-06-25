package com.applications.toms.mimetodoplanificado.ui.screen.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.domain.LineChartEntity
import com.applications.toms.domain.PainScaleCard
import com.applications.toms.domain.PieChartEntity
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.usecases.painscale.GetLineChartHistoryUseCase
import com.applications.toms.usecases.painscale.GetPieChartHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAnalyticsViewModel @Inject constructor(
    private val getLineChartHistoryUseCase: GetLineChartHistoryUseCase,
    private val getPieChartHistoryUseCase: GetPieChartHistoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    fun getLineChartHistoryData(painScales: List<PainScaleCard>) {
        viewModelScope.launch {
            getLineChartHistoryUseCase.execute(Unit)
                .onSuccess {
                    _state.update { state ->
                        state.copy(
                            lineChartData = it
                        )
                    }
                    getPieChartHistoryData(painScales)
                }
                .onFailure {
                    _state.update { state ->
                        state.copy(
                            errorStates = it
                        )
                    }
                }
        }
    }

    private fun getPieChartHistoryData(painScales: List<PainScaleCard>) {
        viewModelScope.launch {
            getPieChartHistoryUseCase.execute(painScales)
                .onSuccess {
                    _state.update { state ->
                        state.copy(
                            pieChartData = it
                        )
                    }
                }
                .onFailure {
                    _state.update { state ->
                        state.copy(
                            errorStates = it
                        )
                    }
                }
        }
    }

    data class State(
        val errorStates: ErrorStates? = null,
        val lineChartData: List<LineChartEntity> = emptyList(),
        val pieChartData: List<PieChartEntity> = emptyList()
    )

}
