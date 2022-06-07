package com.applications.toms.mimetodoplanificado.ui.screen.mymethod.pages.mycycle

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.applications.toms.domain.enums.ErrorStates
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.alarmandnotification.notification.createCycleNotifications
import com.applications.toms.mimetodoplanificado.ui.components.CardButtonMoods
import com.applications.toms.mimetodoplanificado.ui.components.CircularDaysProgress
import com.applications.toms.mimetodoplanificado.ui.components.EmptyStateComponent
import com.applications.toms.mimetodoplanificado.ui.components.customcalendar.Calendar
import com.applications.toms.mimetodoplanificado.ui.components.generics.ButtonType
import com.applications.toms.mimetodoplanificado.ui.components.generics.GenericButton
import com.applications.toms.mimetodoplanificado.ui.components.settings.DatePickerSettingsItem
import com.applications.toms.mimetodoplanificado.ui.screen.mymethod.pages.mycycle.MyCycleViewModel.State
import com.applications.toms.mimetodoplanificado.ui.utils.safeLet
import com.applications.toms.mimetodoplanificado.ui.utils.toCalendarMonth
import com.google.accompanist.pager.ExperimentalPagerApi
import java.time.LocalDate

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun MyCyclePage(
    viewModel: MyCycleViewModel = hiltViewModel(),
    onErrorListener: (String) -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState(State())

    state.errorState?.let { error ->
        onErrorListener(
            when (error) {
                ErrorStates.EMPTY -> stringResource(R.string.snackbar_message_error_empty)
                ErrorStates.NOT_SAVED -> stringResource(R.string.snackbar_message_error_not_saved)
                else -> stringResource(R.string.snackbar_message_error_message)
            }
        )
        viewModel.onResetError()
    }

    MyCycleContent(state) {
        viewModel.saveMyCycle(it)
        createCycleNotifications(context)
    }
}

@ExperimentalMaterialApi
@Composable
fun MyCycleContent(
    state: State,
    onRegisterPeriod: (LocalDate) -> Unit
) {

    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    safeLet(state.startDate, state.endDate) { from, to ->
        val monthFrom = from.toCalendarMonth()
        val monthTo = to.toCalendarMonth()
        val calendarYear = if (monthFrom.monthNumber == monthTo.monthNumber)
            listOf(monthFrom) else listOf(monthFrom, monthTo)

        val totalDays = (from.until(to).days + 1).toFloat()
        val currentDay = (from.until(LocalDate.now()).days + 1).toFloat()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /**
             * Title
             */
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(id = R.dimen.padding_large)),
                text = stringResource(id = R.string.my_cycle_page_title),
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center
            )

            if (state.hasCycleConfigured) {
                /**
                 * Progress Day
                 */
                if (!showDatePicker){
                    Box(modifier = Modifier.weight(1f)) {
                        CircularDaysProgress(
                            modifier = Modifier
                                .padding(dimensionResource(id = R.dimen.padding_medium)),
                            percentage = currentDay.div(totalDays),
                            number = totalDays.toInt()
                        )

                        CardButtonMoods(
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            // TODO on CLICK MOODS
                        }
                    }
                }
            } else {
                EmptyStateComponent()
            }
            /**
             * Info
             */
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.padding_large)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
                    text = when {
                        !state.hasCycleConfigured -> stringResource(R.string.my_cycle_empty_info)
                        currentDay.toInt() == 1 -> stringResource(R.string.my_cycle_msg_first_day)
                        currentDay.toInt() == 21 -> stringResource(R.string.my_cycle_msg_21_day)
                        currentDay.toInt() in 22..28 -> stringResource(R.string.my_cycle_msg_22_to_28_day)
                        currentDay.toInt() > 28 -> stringResource(R.string.my_cycle_msg_29_day)
                        else -> ""
                    },
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center
                )
                GenericButton(
                    buttonType = ButtonType.HIGH_EMPHASIS,
                    text = stringResource(id = R.string.register_my_period_today)
                ) {
                    onRegisterPeriod(LocalDate.now())
                }
            }
            /**
             * Button select date
             */
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(id = R.dimen.padding_large)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                GenericButton(
                    buttonType = ButtonType.LOW_EMPHASIS,
                    text = stringResource(id = R.string.register_my_period)
                ) {
                    showDatePicker = true
                }
            }
            /**
             * Date Picker
             */
            AnimatedVisibility(visible = showDatePicker) {
                DatePickerSettingsItem(
                    date = LocalDate.now(),
                    cycle = true
                ) {
                    showDatePicker = false
                    onRegisterPeriod(it)
                }
            }
            /**
             * Calendar
             */
            AnimatedVisibility(visible = !showDatePicker) {
                Calendar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(id = R.dimen.padding_small)),
                    calendarYear = calendarYear,
                    from = from,
                    to = to,
                    breakDays = 0
                )
            }
        }
    }
}
