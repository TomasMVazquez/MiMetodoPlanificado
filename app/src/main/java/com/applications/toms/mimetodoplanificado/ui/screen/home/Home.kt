package com.applications.toms.mimetodoplanificado.ui.screen.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.applications.toms.domain.MethodCard
import com.applications.toms.domain.enums.Method
import com.applications.toms.domain.enums.UserAction
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.CardButton
import com.applications.toms.mimetodoplanificado.ui.components.DefaultSnackbar
import com.applications.toms.mimetodoplanificado.ui.components.SnackBarType
import com.applications.toms.mimetodoplanificado.ui.screen.settings.Settings
import com.applications.toms.mimetodoplanificado.ui.utils.methods.methods
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun Home(
    appState: HomeAppState = rememberHomeAppState(),
    goToAboutUs: () -> Unit,
    goToMyMethod: () -> Unit
) {
    var snackBarType: SnackBarType by remember { mutableStateOf(SnackBarType.DEFAULT) }

    LaunchedEffect(appState.channel) {
        appState.channel.receiveAsFlow().collect { channel ->
            SnackBarType.values().firstOrNull { type -> type.channel == channel }?.let {
                snackBarType = it
                appState.scaffoldState.snackbarHostState.showSnackbar(
                    message = when (channel) {
                        SnackBarType.ERROR.channel -> appState.context.getString(R.string.snackbar_message_error_message)
                        else -> appState.context.getString(R.string.snackbar_message_generic)
                    }
                )
            }
        }
    }

    BackHandler(appState.modalBottomSheetState.isVisible) {
        appState.hideModalSheet()
    }

    ModalBottomSheetLayout(
        sheetContent = {
            Settings(
                method = appState.state.collectAsState().value.methodAndStartDate,
                onCancel = { type ->
                    type?.let { appState.channel.trySend(it.channel) }
                    appState.hideModalSheet()
                },
                onDone = {
                    appState.onSaveMethod()
                    goToMyMethod()
                }
            )
        },
        sheetState = appState.modalBottomSheetState,
        sheetShape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_bottom_sheet))
    ){
        Scaffold(
            scaffoldState = appState.scaffoldState,
            snackbarHost = { appState.scaffoldState.snackbarHostState }
        ) { paddingValues ->
            Box(modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = dimensionResource(id = R.dimen.padding_large),
                                top = dimensionResource(id = R.dimen.no_padding),
                                end = dimensionResource(id = R.dimen.no_padding),
                                bottom = dimensionResource(id = R.dimen.padding_medium)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {

                        IconButton(
                            onClick = goToAboutUs
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = stringResource(id = R.string.about_us)
                            )
                        }
                    }

                    HomeContent(methods) { method, userAction ->
                        when (userAction) {
                            UserAction.NONE -> {
                                snackBarType = SnackBarType.ERROR
                                appState.channel.trySend(SnackBarType.ERROR.channel)
                            }
                            else -> {
                                appState.setMethodChosen(method)
                            }
                        }
                    }
                }

                DefaultSnackbar(
                    snackbarHostState = appState.scaffoldState.snackbarHostState,
                    onDismiss = {
                        appState.scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    },
                    modifier = Modifier.align(Alignment.BottomCenter),
                    snackBarType = snackBarType
                )
            }
        }
    }
}


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun HomeContent(
    methods: List<MethodCard>,
    onUserAction: (Method, UserAction) -> Unit
) {

    Column(
        modifier = Modifier.padding(
            vertical = dimensionResource(id = R.dimen.no_padding),
            horizontal = dimensionResource(id = R.dimen.padding_large)
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.home_title),
            style = MaterialTheme.typography.h3
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.home_subtitle),
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center
        )

        LazyVerticalGrid(cells = GridCells.Fixed(2)){
            items(methods) {
                CardButton(method = it, onClick = onUserAction)
            }
        }
    }

}