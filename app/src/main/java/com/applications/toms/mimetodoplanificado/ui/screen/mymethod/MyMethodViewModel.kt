package com.applications.toms.mimetodoplanificado.ui.screen.mymethod

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.domain.MethodChosen
import com.applications.toms.mimetodoplanificado.ui.components.SnackBarType
import com.applications.toms.usecases.DeleteChosenMethodUseCase
import com.applications.toms.usecases.GetChosenMethodUseCase
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
    private val getChosenMethodUseCase: GetChosenMethodUseCase,
    private val deleteChosenMethodUseCase: DeleteChosenMethodUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _event.asSharedFlow()

    init {
        getMethod()
    }

    fun getMethod(){
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
                        alarmTime = response.first.alarmTime
                    )
                }
                .onFailure {
                    _event.emit(
                        Event.SnackBarEvent(SnackBarType.ERROR)
                    )
                }
        }
    }

    fun onMethodChangeClick() {
        viewModelScope.launch {
            _event.emit(
                Event.ConfirmMethodChange
            )
        }
    }

    fun onDeleteCurrentMethod() {
        viewModelScope.launch {
            deleteChosenMethodUseCase.execute(Unit)
                .onSuccess {
                    viewModelScope.launch {
                        _event.emit(
                            Event.MethodDeleted
                        )
                    }
                }
                .onFailure {
                    _event.emit(
                        Event.SnackBarEvent(SnackBarType.ERROR)
                    )
                }
        }
    }

    fun onGoToAlarmSettingsClick() {
        viewModelScope.launch {
            _event.emit(
                Event.GoToAlarmSettings
            )
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
        val alarmTime: String? = null
    )

    sealed class Event {
        object ConfirmMethodChange : Event()
        object MethodDeleted : Event()
        object GoToAlarmSettings : Event()
        data class SnackBarEvent(
            val snackBarType: SnackBarType
        ): Event()
    }
}
