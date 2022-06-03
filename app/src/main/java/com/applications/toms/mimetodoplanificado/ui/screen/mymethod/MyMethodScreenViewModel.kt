package com.applications.toms.mimetodoplanificado.ui.screen.mymethod

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.mimetodoplanificado.ui.components.SnackBarType
import com.applications.toms.usecases.method.DeleteChosenMethodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyMethodScreenViewModel @Inject constructor(
    private val deleteChosenMethodUseCase: DeleteChosenMethodUseCase
) : ViewModel() {

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _event.asSharedFlow()

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
                    _event.emit(
                        Event.MethodDeleted
                    )
                }
                .onFailure {
                    onErrorDetected()
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

    fun onErrorDetected(error: String? = null) {
        val typeError = SnackBarType.ERROR
        error?.let {
            typeError.apply {
                text = it
            }
        }
        viewModelScope.launch {
            _event.emit(
                Event.SnackBarEvent(typeError)
            )
        }
    }

    sealed class Event {
        object ConfirmMethodChange : Event()
        object MethodDeleted : Event()
        object GoToAlarmSettings : Event()
        data class SnackBarEvent(
            val snackBarType: SnackBarType
        ) : Event()
    }
}
