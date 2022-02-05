package com.applications.toms.mimetodoplanificado.ui

import android.content.Context
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.applications.toms.mimetodoplanificado.ui.utils.hasOnBoardingAlreadyShown
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): AppState = remember(scaffoldState, navController, coroutineScope) {
    AppState(scaffoldState, navController, coroutineScope)
}

class AppState (
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    private val coroutineScope: CoroutineScope,
) {

    private val context: Context
        @Composable get() = LocalContext.current

    val showOnBoarding: Boolean
        @Composable get() = !hasOnBoardingAlreadyShown(context)

    val currentRoute: String
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""

}