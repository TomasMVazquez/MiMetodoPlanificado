package com.applications.toms.mimetodoplanificado.ui.screen.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.domain.LineChartEntity
import com.applications.toms.domain.PainScaleCard
import com.applications.toms.domain.PainScaleModel
import com.applications.toms.domain.PieChartEntity
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.usecases.painscale.GetPainScaleHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAnalyticsViewModel @Inject constructor(
    private val getPainScaleHistoryUseCase: GetPainScaleHistoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    fun getPainScaleHistoryData(painScales: List<PainScaleCard>) {
        viewModelScope.launch {
            getPainScaleHistoryUseCase.execute(painScales)
                .onSuccess {
                    _state.update { state ->
                        state.copy(
                            painScaleHistory = it.first,
                            lineChartData = it.second,
                            pieChartData = it.third
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
        val painScaleHistory: List<PainScaleModel> = emptyList(),
        val lineChartData: List<LineChartEntity> = emptyList(),
        val pieChartData: List<PieChartEntity> = emptyList()
    )

}
