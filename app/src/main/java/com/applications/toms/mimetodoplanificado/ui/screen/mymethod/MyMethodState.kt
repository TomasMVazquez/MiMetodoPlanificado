package com.applications.toms.mimetodoplanificado.ui.screen.mymethod

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.applications.toms.domain.MethodAndStartDate
import com.applications.toms.domain.enums.Method
import com.applications.toms.mimetodoplanificado.ui.components.SnackBarType
import com.applications.toms.mimetodoplanificado.ui.utils.onSavedMethod
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun rememberMyMethodState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    context: Context = LocalContext.current,
    channel: Channel<Int> = remember { Channel(Channel.CONFLATED) },
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): MyMethodState = remember(coroutineScope) {
    MyMethodState(scaffoldState, context, channel, coroutineScope)
}

@ExperimentalMaterialApi
class MyMethodState(
    val scaffoldState: ScaffoldState,
    val context: Context,
    val channel: Channel<Int>,
    private val coroutineScope: CoroutineScope,
) {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    fun changeOpenDialogState(value: Boolean) {
        _state.value = state.value.copy(
            openDialog = value
        )
    }

    fun addSnackBarType(snackBarType: SnackBarType) {
        _state.value = state.value.copy(
            snackBarType = snackBarType
        )
    }

    data class State(
        var openDialog: Boolean = false,
        var snackBarType: SnackBarType = SnackBarType.DEFAULT
    )
}