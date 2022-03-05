package com.applications.toms.mimetodoplanificado.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import com.applications.toms.domain.MethodAndStartDate
import com.applications.toms.mimetodoplanificado.ui.navigation.Navigation
import com.applications.toms.mimetodoplanificado.ui.screen.settings.Settings
import com.applications.toms.mimetodoplanificado.ui.theme.MiMetodoPlanificadoTheme
import com.applications.toms.mimetodoplanificado.R
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.flow.collect

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun MiMetPlanApp(appState: AppState = rememberAppState()) {

    var methodState by remember { mutableStateOf(MethodAndStartDate()) }

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        appState.state.collect {
            methodState = MethodAndStartDate(
                methodChosen = it.methodChosen,
                startDate = it.startDate
            )
        }
    }

    MiMetPlanScreen {

        ModalBottomSheetLayout(
            sheetContent = {
                Settings(
                    method = methodState,
                    onCancel = { appState.hideModalSheet() },
                    onDone = {
                        appState.hideModalSheet()
                        //TODO GO TO FOLLOWING PAGE
                    }
                )
            },
            sheetState = appState.modalBottomSheetState,
            sheetShape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_bottom_sheet))
        ) {
            Scaffold { paddingValues ->

                Box(modifier = Modifier.padding(paddingValues)) {
                    Navigation(
                        appState = appState
                    )
                }

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