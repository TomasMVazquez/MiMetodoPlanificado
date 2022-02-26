package com.applications.toms.mimetodoplanificado.ui.screen.settings

import androidx.lifecycle.ViewModel
import com.applications.toms.domain.MethodAndStartDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class SettingsViewModel : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: SharedFlow<SettingsState> = _state.asStateFlow()

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

    data class SettingsState (
        val methodAndStartDate: MethodAndStartDate = MethodAndStartDate(),
        val loading: Boolean = false,
        val pillsBreakDays: Int = 5,
        val notifications: Boolean = false,
        val notificationTime: String = "",
        val alarm: Boolean = false,
        val alarmTime: String = ""
    )
}
