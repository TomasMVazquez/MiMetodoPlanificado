package com.applications.toms.mimetodoplanificado.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.toms.data.EitherState
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.domain.MethodAndStartDate
import com.applications.toms.domain.MethodChosen
import com.applications.toms.usecases.SaveChosenMethodUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate

class SettingsViewModel(
    private val saveChosenMethodUseCase: SaveChosenMethodUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: SharedFlow<SettingsState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _event.asSharedFlow()

    fun changeLoading(show: Boolean) {
        _state.value = _state.value.copy(loading = show)
    }

    fun setMethodChosen(methodChosen: MethodAndStartDate) {
        _state.value = _state.value.copy(methodAndStartDate = methodChosen)
    }

    fun changeStartDate(date: LocalDate) {
        _state.value = _state.value.copy(
            methodAndStartDate = _state.value.methodAndStartDate.copy(
                startDate = date
            )
        )
    }

    fun changePillsBreakDays(days: Int) {
        _state.value = _state.value.copy(pillsBreakDays = days)
    }

    fun changeNotificationValue(value: Boolean, time: String) {
        _state.value = _state.value.copy(notifications = value, notificationTime = time)
    }

    fun changeAlarmValue(value: Boolean, time: String) {
        _state.value = _state.value.copy(alarm = value, alarmTime = time)
    }

    fun onSaveMethodChosen(methodChosen: MethodChosen) {
        viewModelScope.launch {
            saveChosenMethodUseCase.prepare(methodChosen).collect { result ->
                result.onSuccess {
                    _event.emit(Event.Continue(state = it))
                }
                result.onFailure {
                    _event.emit(Event.Continue(state = it))
                }
            }
        }
    }

    data class SettingsState (
        val methodAndStartDate: MethodAndStartDate = MethodAndStartDate(),
        val loading: Boolean = false,
        val pillsBreakDays: Int = 5,
        val notifications: Boolean = false,
        val notificationTime: String = "",
        val alarm: Boolean = false,
        val alarmTime: String = ""
    )

    sealed class Event {
        data class Continue (
            val state: EitherState
        ) : Event()
    }
}
