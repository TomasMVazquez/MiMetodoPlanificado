package com.applications.toms.mimetodoplanificado.ui.screen.mymethod

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.applications.toms.mimetodoplanificado.ui.components.MyLoadingContent
import com.applications.toms.mimetodoplanificado.ui.components.customcalendar.Calendar
import com.applications.toms.mimetodoplanificado.ui.components.generics.GenericSpacer
import com.applications.toms.mimetodoplanificado.ui.components.generics.SpacerType
import com.applications.toms.mimetodoplanificado.ui.screen.mymethod.MyMethodViewModel.State
import com.applications.toms.mimetodoplanificado.ui.theme.VividRaspberry
import com.applications.toms.mimetodoplanificado.ui.utils.methods.TOTAL_CYCLE_DAYS
import com.applications.toms.mimetodoplanificado.ui.utils.safeLet
import com.applications.toms.mimetodoplanificado.ui.utils.toCalendarMonth
import java.time.LocalDate

@Composable
fun MyMethod(viewModel: MyMethodViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState(State())

    if (!state.loading)
        MyMethodContent(state)
    else
        AnimatedVisibility(visible = state.loading) {
            MyLoadingContent(Modifier.fillMaxSize())
        }

}

@Composable
fun MyMethodContent(state: State) {
    /**
     * TODO MANAGE ERROR
     */
    safeLet(state.startDate, state.endDate) { from, to ->
        val monthFrom = from.toCalendarMonth()
        val monthTo = to.toCalendarMonth()
        val calendarYear = if (monthFrom.monthNumber == monthTo.monthNumber)
            listOf(monthFrom) else listOf(monthFrom, monthTo)

        LazyColumn {

            item {
                GenericSpacer(
                    type = SpacerType.HORIZONTAL,
                    padding = 2.dp,
                    backgroundColor = VividRaspberry
                )

                Calendar(
                    calendarYear = calendarYear,
                    from = from,
                    to = to
                )
            }

        }
    }

}

