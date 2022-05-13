package com.applications.toms.mimetodoplanificado.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.applications.toms.mimetodoplanificado.ui.navigation.Navigation
import com.applications.toms.mimetodoplanificado.ui.theme.MiMetodoPlanificadoTheme
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun MiMetPlanApp(appState: AppState = rememberAppState()) {

    MiMetPlanScreen {

        Scaffold { paddingValues ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Navigation(
                    appState.navController,
                    appState.state.collectAsState().value.navigationState,
                    onChangeNavigationState = {
                        appState.onNavigationStateChange(it)
                    }
                )
            }
        }
    }
}



@Composable
fun MiMetPlanScreen(content: @Composable () -> Unit) {
    MiMetodoPlanificadoTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}