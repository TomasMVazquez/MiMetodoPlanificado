package com.applications.toms.mimetodoplanificado.ui

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.applications.toms.mimetodoplanificado.ui.navigation.NavigationState
import com.applications.toms.mimetodoplanificado.ui.utils.hasOnBoardingAlreadyShown
import com.applications.toms.mimetodoplanificado.ui.utils.isMethodSaved
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@ExperimentalMaterialApi
@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current
): AppState = remember(navController) {
    AppState(navController, context)
}

@ExperimentalMaterialApi
class AppState(
    val navController: NavHostController,
    val context: Context,
) {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    init {
        _state.value = state.value.copy(
            navigationState = if (!hasOnBoardingAlreadyShown(context)) NavigationState.ON_BOARDING
            else if (isMethodSaved(context)) NavigationState.MY_METHOD
            else NavigationState.HOME
        )
    }

    fun onNavigationStateChange(newState: NavigationState) {
        _state.value = state.value.copy(
            navigationState = newState
        )
    }

    data class State(
        var navigationState: NavigationState = NavigationState.ON_BOARDING
    )

}
