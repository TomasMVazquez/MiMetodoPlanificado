package com.applications.toms.mimetodoplanificado.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.applications.toms.domain.MethodAndStartDate
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.DefaultSnackbar
import com.applications.toms.mimetodoplanificado.ui.components.SnackBarType
import com.applications.toms.mimetodoplanificado.ui.navigation.Navigation
import com.applications.toms.mimetodoplanificado.ui.screen.settings.Settings
import com.applications.toms.mimetodoplanificado.ui.theme.MiMetodoPlanificadoTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun MiMetPlanApp(appState: AppState = rememberAppState()) {

    var snackBarType by remember { mutableStateOf(SnackBarType.DEFAULT) }

    LaunchedEffect(appState.channel) {
        appState.channel.receiveAsFlow().collect {
            appState.scaffoldState.snackbarHostState.showSnackbar(
                message = when (it) {
                    SnackBarType.ERROR.channel -> appState.context.getString(R.string.snackbar_message_error_message)
                    else -> appState.context.getString(R.string.snackbar_message_generic)
                }
            )
        }
    }

    MiMetPlanScreen {

        ModalBottomSheetLayout(
            sheetContent = {
                Settings(
                    method = appState.state.collectAsState().value.methodAndStartDate,
                    onCancel = {
                        it?.let { type ->
                            snackBarType = type
                            appState.channel.trySend(type.channel)
                        }
                        appState.hideModalSheet()
                    },
                    onDone = {
                        appState.onSaveMethod()
                    }
                )
            },
            sheetState = appState.modalBottomSheetState,
            sheetShape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_bottom_sheet))
        ) {
            Scaffold(scaffoldState = appState.scaffoldState, snackbarHost = { appState.scaffoldState.snackbarHostState }) { paddingValues ->

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Navigation(
                        appState.navController,
                        appState.state.collectAsState().value.showOnBoarding,
                        appState.state.collectAsState().value.isSaved,
                        onFinishOnBoarding = {
                            appState.onBoardingFinish()
                        },
                        goToSettings = {
                            appState.setMethodChosen(it)
                            appState.showModalSheet()
                        },
                        onMethodChanged = {
                            appState.onMethodChange()
                        }
                    )

                    DefaultSnackbar(snackbarHostState = appState.scaffoldState.snackbarHostState, onDismiss = {
                        appState.scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    },modifier = Modifier.align(Alignment.BottomCenter), snackBarType = snackBarType)
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