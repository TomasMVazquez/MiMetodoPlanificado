package com.applications.toms.mimetodoplanificado.ui.screen.analytics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.EmptyStateComponent
import com.applications.toms.mimetodoplanificado.ui.components.analytics.LineChart
import com.applications.toms.mimetodoplanificado.ui.components.painscale.CardPain
import com.applications.toms.mimetodoplanificado.ui.screen.analytics.MyAnalyticsViewModel.State
import com.applications.toms.mimetodoplanificado.ui.theme.LightBlack
import com.applications.toms.mimetodoplanificado.ui.theme.Purple
import com.applications.toms.mimetodoplanificado.ui.theme.VividRaspberry
import com.applications.toms.mimetodoplanificado.ui.utils.painscale.painScaleCards

@ExperimentalMaterialApi
@Composable
fun MyAnalytics(
    viewModel: MyAnalyticsViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    val state by viewModel.state.collectAsState(State())

    AnalyticsContent(
        state = state,
        goBack = goBack
    )
}

@ExperimentalMaterialApi
@Composable
fun AnalyticsContent(
    state: State,
    goBack: () -> Unit
) {
    val painScales = painScaleCards

    Column(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_tiny))) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = goBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.content_description_back),
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }

        /**
         * Title
         */
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.padding_large),
                    bottom = dimensionResource(id = R.dimen.padding_medium)
                ),
            text = stringResource(id = R.string.analytics),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onPrimary,
            textAlign = TextAlign.Start
        )

        when {
            state.errorStates != null -> {
                EmptyStateComponent(stringResource(R.string.error_analytics_text))
            }
            state.lineChartData.isEmpty() -> {
                EmptyStateComponent(textToShow = stringResource(R.string.empty_analytics_text))
            }
            else -> {
                LazyColumn {
                    /**
                     * Reference
                     */
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = dimensionResource(id = R.dimen.padding_small),
                                    bottom = dimensionResource(id = R.dimen.padding_medium)
                                ),
                            text = stringResource(id = R.string.pain_reference_title),
                            style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.onPrimary,
                            textAlign = TextAlign.Start
                        )

                        LazyRow {
                            items(painScales) {
                                CardPain(
                                    modifier = Modifier
                                        .width(dimensionResource(id = R.dimen.card_pain_width))
                                        .padding(dimensionResource(id = R.dimen.padding_small)),
                                    painScaleCard = it,
                                    showPainScaleNumber = true,
                                    selectedPainScaleCard = -1,
                                    onClickCard = { }
                                )
                            }
                        }
                    }

                    /**
                     * Pain Chart
                     */
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = dimensionResource(id = R.dimen.padding_large),
                                    start = dimensionResource(id = R.dimen.padding_small),
                                    bottom = dimensionResource(id = R.dimen.padding_small)
                                ),
                            text = stringResource(id = R.string.pain_line_chart_title),
                            style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.onPrimary,
                            textAlign = TextAlign.Start
                        )

                        LineChart(
                            lineChartData = state.lineChartData,
                            verticalAxisValues = listOf(0f, 1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f),
                            verticalAxisLabelColor = VividRaspberry,
                            horizontalAxisLabelColor = Purple,
                            lineColor = LightBlack,
                            verticalAxisLabelFontSize = 12.sp,
                            horizontalAxisLabelFontSize = 10.sp,
                            isShowVerticalAxis = true
                        )
                    }
                }
            }
        }
    }
}