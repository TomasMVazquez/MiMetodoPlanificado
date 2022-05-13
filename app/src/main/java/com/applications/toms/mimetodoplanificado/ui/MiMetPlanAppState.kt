package com.applications.toms.mimetodoplanificado.ui

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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.applications.toms.domain.MethodAndStartDate
import com.applications.toms.domain.enums.Method
import com.applications.toms.mimetodoplanificado.ui.utils.hasOnBoardingAlreadyShown
import com.applications.toms.mimetodoplanificado.ui.utils.isMethodSaved
import com.applications.toms.mimetodoplanificado.ui.utils.onSavedMethod
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    modalBottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    channel: Channel<Int> = remember { Channel<Int>(Channel.CONFLATED) },
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): AppState = remember(navController, coroutineScope) {
    AppState(scaffoldState, modalBottomSheetState, navController, context, lifecycleOwner, channel, coroutineScope)
}

@ExperimentalMaterialApi
class AppState(
    val scaffoldState: ScaffoldState,
    val modalBottomSheetState: ModalBottomSheetState,
    val navController: NavHostController,
    val context: Context,
    val lifecycleOwner: LifecycleOwner,
    val channel: Channel<Int>,
    private val coroutineScope: CoroutineScope,
) {

    private val _state = MutableStateFlow(MethodAndStartDate())
    val state: SharedFlow<MethodAndStartDate> = _state.asStateFlow()

    val isSaved: Boolean = isMethodSaved(context)

    val showOnBoarding: Boolean = !hasOnBoardingAlreadyShown(context)

    fun onSaveMethod() {
        onSavedMethod(context)
        hideModalSheet()
    }

    fun hideModalSheet() {
        coroutineScope.launch { modalBottomSheetState.hide() }
    }

    fun showModalSheet() {
        coroutineScope.launch {
            modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
        }
    }

    fun setMethodChosen(method: Method) {
        _state.value = MethodAndStartDate(
            methodChosen = method
        )
    }

}