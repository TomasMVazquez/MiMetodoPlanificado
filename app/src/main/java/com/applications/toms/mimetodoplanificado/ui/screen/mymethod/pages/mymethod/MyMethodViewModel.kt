package com.applications.toms.mimetodoplanificado.ui.screen.mymethod.pages.mymethod

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.domain.MethodChosen
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.usecases.method.GetChosenMethodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
                    _state.value = State(
                        loading = false,
                        methodChosen = response.first,
                        startDate = response.first.methodAndStartDate.startDate,
                        endDate = response.first.methodAndStartDate.startDate.plusDays(response.first.totalDaysCycle - 1),
                        nextCycle = response.second,
                        breakDays = response.first.breakDays,
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
        val isNotificationEnable: Boolean? = null,
        val notificationTime: String? = null,
        val isAlarmEnable: Boolean? = null,
        val alarmTime: String? = null,
        val errorState: ErrorStates? = null
    )

}
