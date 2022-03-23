package com.applications.toms.mimetodoplanificado.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import com.applications.toms.domain.MethodAndStartDate
import com.applications.toms.mimetodoplanificado.ui.navigation.Navigation
import com.applications.toms.mimetodoplanificado.ui.screen.settings.Settings
import com.applications.toms.mimetodoplanificado.ui.theme.MiMetodoPlanificadoTheme
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.utils.hasOnBoardingAlreadyShown
import com.applications.toms.mimetodoplanificado.ui.utils.isMethodSaved
import com.applications.toms.mimetodoplanificado.ui.utils.onMethodHasBeenSaved
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.flow.collect

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun MiMetPlanApp(appState: AppState = rememberAppState()) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var methodState by remember { mutableStateOf(MethodAndStartDate()) }
    var shouldShowOnBoarding by rememberSaveable { mutableStateOf(!hasOnBoardingAlreadyShown(context)) }
    var isMethodSaved by rememberSaveable { mutableStateOf(isMethodSaved(context)) }


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
                        onMethodHasBeenSaved(context)
                        isMethodSaved = true
                        appState.hideModalSheet()
                    }
                )
            },
            sheetState = appState.modalBottomSheetState,
            sheetShape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_bottom_sheet))
        ) {
            Scaffold { paddingValues ->

                Box(modifier = Modifier.padding(paddingValues)) {
                    Navigation(
                        appState.navController,
                        shouldShowOnBoarding,
                        isMethodSaved,
                        onFinishOnBoarding = {
                             shouldShowOnBoarding = false
                        },
                        goToSettings = {
                            appState.setMethodChosen(it)
                            appState.showModalSheet()
                        },
                        onMethodChanged = {
                            isMethodSaved = false
                        }
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