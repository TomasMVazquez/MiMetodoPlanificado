package com.applications.toms.mimetodoplanificado.ui.screen.mymethod

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.applications.toms.mimetodoplanificado.ui.components.SnackBarType
import com.applications.toms.mimetodoplanificado.ui.utils.isOnlyCycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun rememberMyMethodState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    context: Context = LocalContext.current,
    pagerState: PagerState = rememberPagerState(0),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): MyMethodState = remember(coroutineScope) {
    MyMethodState(scaffoldState, context, pagerState, coroutineScope)
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
class MyMethodState(
    val scaffoldState: ScaffoldState,
    val context: Context,
    val pagerState: PagerState,
    private val coroutineScope: CoroutineScope,
) {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    init {
        _state.update { state ->
            state.copy(
                isOnlyCycle = isOnlyCycle(context)
            )
        }
    }

    fun changeOpenDialogState(value: Boolean) {
        _state.update { state ->
            state.copy(
                openDialog = value
            )
        }
    }

    fun addSnackBarType(snackBarType: SnackBarType) {
        _state.update { state ->
            state.copy(
                snackBarType = snackBarType
            )
        }
    }

    data class State(
        var openDialog: Boolean = false,
        var snackBarType: SnackBarType = SnackBarType.DEFAULT,
        var isOnlyCycle: Boolean = false
    )
}