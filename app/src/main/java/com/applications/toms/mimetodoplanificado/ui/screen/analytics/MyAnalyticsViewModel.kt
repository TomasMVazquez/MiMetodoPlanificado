package com.applications.toms.mimetodoplanificado.ui.screen.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.domain.LineChartEntity
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.usecases.painscale.GetLineChartHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAnalyticsViewModel @Inject constructor(
    private val getLineChartHistoryUseCase: GetLineChartHistoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    init {
        getHistoryData()
    }

    private fun getHistoryData() {
        viewModelScope.launch {
            getLineChartHistoryUseCase.execute(Unit)
                .onSuccess {
                    _state.update { state ->
                        state.copy(
                            lineChartData = it
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
        val lineChartData: List<LineChartEntity> = emptyList()
    )

}
