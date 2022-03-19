package com.applications.toms.mimetodoplanificado.ui.screen.mymethod

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.domain.enums.Method
import com.applications.toms.mimetodoplanificado.ui.utils.methods.TOTAL_CYCLE_DAYS
import com.applications.toms.usecases.GetChosenMethodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MyMethodViewModel @Inject constructor(
    private val getChosenMethodUseCase: GetChosenMethodUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: SharedFlow<State> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getChosenMethodUseCase.execute(Unit)
                .onSuccess { response ->
                    _state.value = State(
                        loading = false,
                        methodChosen = response.first.methodAndStartDate.methodChosen,
                        startDate = response.first.methodAndStartDate.startDate,
                        endDate = response.first.methodAndStartDate.startDate.plusDays(TOTAL_CYCLE_DAYS),
                        nextCycle = response.second,
                        pillsBreakDays = response.first.pillsBreakDays,
                        notifications = response.first.notifications,
                        notificationTime = response.first.notificationTime,
                        alarm = response.first.alarm,
                        alarmTime =response.first.alarmTime
                    )
                }
                .onFailure {
                    it
                    /**
                     * TODO MANAGE ERROR
                     */
                }
        }
    }

    data class State(
        val loading: Boolean = true,
        val methodChosen: Method? = null,
        val startDate: LocalDate? = null,
        val endDate: LocalDate? = null,
        val nextCycle: LocalDate? = null,
        val pillsBreakDays: Int? = null,
        val notifications: Boolean? = null,
        val notificationTime: String? = null,
        val alarm: Boolean? = null,
        val alarmTime: String? = null
    )

}
