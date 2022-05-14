package com.applications.toms.mimetodoplanificado.ui.screen.home

import android.content.Context
import androidx.compose.animation.core.TweenSpec
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
import com.applications.toms.mimetodoplanificado.ui.utils.onSavedMethod
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun rememberHomeAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    modalBottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
    context: Context = LocalContext.current,
    channel: Channel<Int> = remember { Channel(Channel.CONFLATED) },
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): HomeAppState = remember(coroutineScope) {
    HomeAppState(scaffoldState, modalBottomSheetState, context, channel, coroutineScope)
}

@ExperimentalMaterialApi
class HomeAppState(
    val scaffoldState: ScaffoldState,
    val modalBottomSheetState: ModalBottomSheetState,
    val context: Context,
    val channel: Channel<Int>,
    private val coroutineScope: CoroutineScope,
) {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    fun onSaveMethod() {
        onSavedMethod(context)
        hideModalSheet()
    }

    fun hideModalSheet() {
        coroutineScope.launch {
            modalBottomSheetState.animateTo(ModalBottomSheetValue.Hidden, TweenSpec(durationMillis = 800, delay = 10))
        }
    }

    private fun showModalSheet() {
        coroutineScope.launch {
            modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded, TweenSpec(durationMillis = 800, delay = 10))
        }
    }

    fun setMethodChosen(method: Method) {
        _state.value = state.value.copy(
            methodAndStartDate = MethodAndStartDate(
                methodChosen = method
            )
        )
        showModalSheet()
    }

    data class State(
        var methodAndStartDate: MethodAndStartDate = MethodAndStartDate()
    )
}