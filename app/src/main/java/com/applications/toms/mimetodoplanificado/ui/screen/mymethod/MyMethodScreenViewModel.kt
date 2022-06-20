package com.applications.toms.mimetodoplanificado.ui.screen.mymethod

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.toms.data.onFailure
import com.applications.toms.data.onSuccess
import com.applications.toms.mimetodoplanificado.ui.components.SnackBarType
import com.applications.toms.usecases.method.DeleteChosenMethodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyMethodScreenViewModel @Inject constructor(
    private val deleteChosenMethodUseCase: DeleteChosenMethodUseCase
) : ViewModel() {

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _event.asSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

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
                    showSnackBar(SnackBarType.ERROR)
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

    fun showSnackBar(type: SnackBarType, msg: String? = null) {
        emitEffect(
            Effect.SnackBarEvent(
                snackBarType = type,
                msg = msg
            )
        )
    }

    private fun emitEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.send(
                effect
            )
        }
    }

    sealed class Event {
        object ConfirmMethodChange : Event()
        object MethodDeleted : Event()
        object GoToAlarmSettings : Event()
    }

    sealed class Effect {
        data class SnackBarEvent(
            val snackBarType: SnackBarType,
            val msg: String?
        ) : Effect()
    }
}
