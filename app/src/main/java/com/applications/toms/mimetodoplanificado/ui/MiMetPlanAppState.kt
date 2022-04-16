package com.applications.toms.mimetodoplanificado.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.applications.toms.domain.MethodAndStartDate
import com.applications.toms.domain.enums.Method
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun rememberAppState(
    modalBottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): AppState = remember(navController, coroutineScope) {
    AppState(modalBottomSheetState, navController, coroutineScope)
}

@ExperimentalMaterialApi
class AppState (
    val modalBottomSheetState: ModalBottomSheetState,
    val navController: NavHostController,
    private val coroutineScope: CoroutineScope,
) {

    private val _state = MutableStateFlow(MethodAndStartDate())
    val state: SharedFlow<MethodAndStartDate> = _state.asStateFlow()

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