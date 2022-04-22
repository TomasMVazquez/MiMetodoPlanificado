package com.applications.toms.mimetodoplanificado.ui.screen.alarmsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.domain.MethodChosen
import com.applications.toms.usecases.GetChosenMethodUseCase
import com.applications.toms.usecases.UpdateChosenMethodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmSettingsViewModel @Inject constructor(
    private val getChosenMethodUseCase: GetChosenMethodUseCase,
    private val updateChosenMethodUseCase: UpdateChosenMethodUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AlarmSettingsState())
    val state: SharedFlow<AlarmSettingsState> = _state.asStateFlow()

    lateinit var methodChosen: MethodChosen

    init {
        viewModelScope.launch {
            getChosenMethodUseCase.execute(Unit)
                .onSuccess {
                    methodChosen = it.first
                    _state.value = _state.value.copy(
                        dataRetrieved = true,
                        isNotificationEnable = it.first.isNotificationEnable,
                        notificationTime = it.first.notificationTime,
                        isAlarmEnable = it.first.isAlarmEnable,
                        alarmTime = it.first.alarmTime
                    )
                }
                .onFailure {
                    _state.value = _state.value.copy(error = true)
                }
        }
    }

    fun changeNotificationValue(enable: Boolean, time: String?) {
        _state.value = _state.value.copy(
            changeStateNotSaved = true,
            hasNotificationChange = true,
            isNotificationEnable = enable,
            notificationTime = time
        )
    }

    fun changeAlarmValue(enable: Boolean, time: String?) {
        _state.value = _state.value.copy(
            changeStateNotSaved = true,
            hasAlarmChange = true,
            isAlarmEnable = enable,
            alarmTime = time
        )
    }

    fun onSaveMethodChosen(
        notificationsState: Boolean,
        notificationTime: String?,
        alarmState: Boolean,
        alarmTime: String?
    ) {
        viewModelScope.launch {
            updateChosenMethodUseCase.execute(
                UpdateChosenMethodUseCase.Input(
                    methodChosen = methodChosen,
                    notificationsState = notificationsState,
                    notificationTime = notificationTime,
                    alarmState = alarmState,
                    alarmTime = alarmTime
                )
            )
                .onSuccess {
                    _state.value = _state.value.copy(
                        changesSaved = true,
                        changeStateNotSaved = false
                    )
                }
                .onFailure {
                    _state.value = _state.value.copy(error = true)
                }
        }
    }

    data class AlarmSettingsState(
        val dataRetrieved: Boolean = false,
        val error: Boolean = false,
        val changeStateNotSaved: Boolean = false,
        val changesSaved: Boolean = false,
        val hasNotificationChange: Boolean = false,
        val isNotificationEnable: Boolean = false,
        val notificationTime: String? = null,
        val hasAlarmChange: Boolean = false,
        val isAlarmEnable: Boolean = false,
        val alarmTime: String? = null
    )
}
