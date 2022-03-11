package com.applications.toms.mimetodoplanificado.ui

import android.content.Context
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.applications.toms.domain.enums.Method
import com.applications.toms.domain.MethodAndStartDate
import com.applications.toms.mimetodoplanificado.ui.navigation.NavCommand
import com.applications.toms.mimetodoplanificado.ui.navigation.NavFeature
import com.applications.toms.mimetodoplanificado.ui.utils.hasOnBoardingAlreadyShown
import com.applications.toms.mimetodoplanificado.ui.utils.isMethodSaved
import kotlinx.coroutines.CoroutineScope
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
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): AppState = remember(scaffoldState, navController, coroutineScope) {
    AppState(scaffoldState, modalBottomSheetState, navController, coroutineScope)
}

@ExperimentalMaterialApi
class AppState (
    val scaffoldState: ScaffoldState,
    val modalBottomSheetState: ModalBottomSheetState,
    val navController: NavHostController,
    private val coroutineScope: CoroutineScope,
) {

    private val _state = MutableStateFlow(MethodAndStartDate())
    val state: SharedFlow<MethodAndStartDate> = _state.asStateFlow()

    private val context: Context
        @Composable get() = LocalContext.current

    val showOnBoarding: Boolean
        @Composable get() = !hasOnBoardingAlreadyShown(context)

    val isMethodSaved: Boolean
        @Composable get() = isMethodSaved(context)

    val currentRoute: String
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""

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

    fun goToMyMethodHome() {
        navController.navigate(NavCommand.ContentType(NavFeature.MY_METHOD).route)
        hideModalSheet()
    }

}