package com.applications.toms.mimetodoplanificado.ui.screen.mymethod.pages.mymethod

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.domain.MethodChosen
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.usecases.method.GetChosenMethodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MyMethodViewModel @Inject constructor(
    private val getChosenMethodUseCase: GetChosenMethodUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    init {
        getMethodData()
    }

    fun getMethodData() {
        viewModelScope.launch {
            getChosenMethodUseCase.execute(Unit)
                .onSuccess { response ->
                    val endDate = response.first.methodAndStartDate.startDate.plusDays(response.first.totalDaysCycle - 1)
                    _state.value = State(
                        loading = false,
                        methodChosen = response.first,
                        startDate = response.first.methodAndStartDate.startDate,
                        endDate = endDate,
                        nextCycle = response.second,
                        breakDays = response.first.breakDays,
                        totalDays = response.first.methodAndStartDate.startDate.until(response.second).days + 1,
                        currentDay = response.first.methodAndStartDate.startDate.until(LocalDate.now()).days + 1,
                        breakDayStarts = endDate.minusDays(response.first.breakDays.toLong() - 1),
                        isNotificationEnable = response.first.isNotificationEnable,
                        notificationTime = response.first.notificationTime,
                        isAlarmEnable = response.first.isAlarmEnable,
                        alarmTime = response.first.alarmTime,
                        errorState = null
                    )
                }
                .onFailure {
                    _state.value = State(
                        loading = false,
                        errorState = it
                    )
                }
        }
    }

    data class State(
        val loading: Boolean = true,
        val methodChosen: MethodChosen? = null,
        val startDate: LocalDate? = null,
        val endDate: LocalDate? = null,
        val nextCycle: LocalDate? = null,
        val breakDays: Int? = null,
        val totalDays: Int = -1,
        val currentDay: Int = -1,
        val breakDayStarts: LocalDate? = null,
        val isNotificationEnable: Boolean? = null,
        val notificationTime: String? = null,
        val isAlarmEnable: Boolean? = null,
        val alarmTime: String? = null,
        val errorState: ErrorStates? = null
    )

}
