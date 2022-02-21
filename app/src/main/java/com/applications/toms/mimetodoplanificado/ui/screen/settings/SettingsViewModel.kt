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

    fun changePillsBreakDays(days: Int) {
        state = state.copy(pillsBreakDays = days)
    }

    fun changeNotificationValue(value: Boolean, time: String) {
        state = state.copy(notifications = value, notificationTime = time)
    }

    fun changeAlarmValue(value: Boolean, time: String) {
        state = state.copy(alarm = value, alarmTime = time)
    }

    data class SettingsState (
        val methodType: Method? = null,
        val loading: Boolean = false,
        val startDate: LocalDate = LocalDate.now(),
        val pillsBreakDays: Int = 5,
        val notifications: Boolean = false,
        val notificationTime: String = "",
        val alarm: Boolean = false,
        val alarmTime: String = ""
    )
}