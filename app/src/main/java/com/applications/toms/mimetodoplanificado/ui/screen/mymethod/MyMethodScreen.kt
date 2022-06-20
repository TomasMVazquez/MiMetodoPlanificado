package com.applications.toms.mimetodoplanificado.ui.screen.mymethod

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.DefaultSnackbar
import com.applications.toms.mimetodoplanificado.ui.components.MyMethodCustomToolbar
import com.applications.toms.mimetodoplanificado.ui.components.dialogs.AlertDialogConfirmMethodChange
import com.applications.toms.mimetodoplanificado.ui.screen.mymethod.pages.mycycle.MyCyclePage
import com.applications.toms.mimetodoplanificado.ui.screen.mymethod.pages.mymethod.MyMethodPage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import kotlinx.coroutines.flow.collect

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MyMethodScreen(
    appState: MyMethodState = rememberMyMethodState(),
    viewModel: MyMethodScreenViewModel = hiltViewModel(),
    onMethodDeleted: () -> Unit,
    goToAlarmSettings: () -> Unit
) {

    val isOnlyCycle = appState.state.collectAsState().value.isOnlyCycle

    LaunchedEffect(key1 = viewModel.event) {
        viewModel.event.collect {
            when (it) {
                MyMethodScreenViewModel.Event.ConfirmMethodChange -> {
                    appState.changeOpenDialogState(true)
                }
                MyMethodScreenViewModel.Event.MethodDeleted -> {
                    appState.changeOpenDialogState(false)
                    onMethodDeleted()
                }
                MyMethodScreenViewModel.Event.GoToAlarmSettings -> {
                    goToAlarmSettings()
                }
            }
        }
    }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collect {
            when (it) {
                is MyMethodScreenViewModel.Effect.SnackBarEvent -> {
                    appState.addSnackBarType(it.snackBarType)
                    appState.scaffoldState.snackbarHostState.showSnackbar(
                        message = it.msg ?: appState.context.getString(R.string.snackbar_message_error_message)
                    )
                }
            }
        }
    }

    if (appState.state.collectAsState().value.openDialog)
        AlertDialogConfirmMethodChange(
            onCancel = { appState.changeOpenDialogState(false) },
            onConfirm = {
                if (!isOnlyCycle)
                    viewModel.onDeleteCurrentMethod()
                else {
                    appState.changeOpenDialogState(false)
                    onMethodDeleted()
                }
            }
        )

    Scaffold(
        scaffoldState = appState.scaffoldState,
        snackbarHost = { appState.scaffoldState.snackbarHostState }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column() {
                MyMethodCustomToolbar(
                    onChangeMethodClick = { viewModel.onMethodChangeClick() },
                    onGoToAlarmSettingsClick = { viewModel.onGoToAlarmSettingsClick() }
                )

                HorizontalPagerIndicator(
                    pagerState = appState.pagerState,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .align(Alignment.CenterHorizontally),
                    activeColor = MaterialTheme.colors.secondary,
                    inactiveColor = MaterialTheme.colors.secondary.copy(ContentAlpha.disabled)
                )

                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    count = if (isOnlyCycle) 1 else 2,
                    state = appState.pagerState
                ) { page ->
                    if (isOnlyCycle || page == 1) {
                        MyCyclePage() { type, msg ->
                            viewModel.showSnackBar(type, msg)
                        }
                    } else
                        MyMethodPage(context = appState.context)
                }
            }

            DefaultSnackbar(
                snackbarHostState = appState.scaffoldState.snackbarHostState,
                onDismiss = {
                    appState.scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                },
                modifier = Modifier.align(Alignment.BottomCenter),
                snackBarType = appState.state.collectAsState().value.snackBarType
            )
        }
    }

}
