package com.applications.toms.mimetodoplanificado.ui.screen.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.applications.toms.domain.Method
import java.time.LocalDate

class SettingsViewModel : ViewModel() {

    var state by mutableStateOf(SettingsState())
        private set

    fun changeLoading(show: Boolean) {
        state = state.copy(loading = show)
    }

    fun setMethodChosen(methodChosen: Method) {
        state = state.copy(methodType = methodChosen)
    }

    fun changeStartDate(date: LocalDate) {
        state = state.copy(startDate = date)
    }

    fun changeShowDatePicker(show: Boolean) {
        state = state.copy(showDatePicker = show)
    }

    fun changePillsBreakDays(days: Int) {
        state = state.copy(pillsBreakDays = days)
    }

    fun changeNotificationValue(value: Boolean) {
        state = state.copy(notifications = value)
    }

    fun changeAlarmValue(value: Boolean) {
        state = state.copy(alarm = value)
    }

    data class SettingsState (
        val methodType: Method? = null,
        val loading: Boolean = false,
        val showDatePicker: Boolean = false,
        val startDate: LocalDate = LocalDate.now(),
        val pillsBreakDays: Int = 5,
        val notifications: Boolean = true,
        val alarm: Boolean = true
    )
}