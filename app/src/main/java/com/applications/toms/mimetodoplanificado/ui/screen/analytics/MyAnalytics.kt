package com.applications.toms.mimetodoplanificado.ui.screen.analytics

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.applications.toms.domain.PainScaleCard
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.EmptyStateComponent
import com.applications.toms.mimetodoplanificado.ui.components.analytics.LineChart
import com.applications.toms.mimetodoplanificado.ui.components.analytics.PieChart
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
    val painScales = painScaleCards
    val state by viewModel.state.collectAsState(State())

    viewModel.getPainScaleHistoryData(painScales)

    AnalyticsContent(
        state = state,
        painScales = painScales,
        goBack = goBack
    )
}

@ExperimentalMaterialApi
@Composable
fun AnalyticsContent(
    state: State,
    painScales: List<PainScaleCard>,
    goBack: () -> Unit
) {
    var expandReference by rememberSaveable { mutableStateOf(true) }

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
        }

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
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        start = dimensionResource(id = R.dimen.padding_medium),
                                        bottom = dimensionResource(id = R.dimen.padding_medium)
                                    ),
                                text = stringResource(id = R.string.pain_reference_title),
                                style = MaterialTheme.typography.h6,
                                color = MaterialTheme.colors.onPrimary,
                                textAlign = TextAlign.Start
                            )
                            
                            IconToggleButton(
                                checked = expandReference,
                                onCheckedChange = { expandReference = it }
                            ) {
                                Icon(
                                    imageVector = if(expandReference) Icons.Default.ExpandMore else Icons.Default.ExpandLess,
                                    contentDescription = stringResource(R.string.content_description_expand_more),
                                    tint = MaterialTheme.colors.onBackground
                                )
                            }
                        }

                        AnimatedVisibility(visible = expandReference) {
                            LazyRow {
                                items(painScales) {
                                    CardPain(
                                        modifier = Modifier
                                            .width(dimensionResource(id = R.dimen.card_pain_width))
                                            .padding(dimensionResource(id = R.dimen.padding_small)),
                                        painScaleCard = it,
                                        showPainScaleNumber = true,
                                        selectedPainScaleCard = null,
                                        onClickCard = { }
                                    )
                                }
                            }   
                        }
                    }

                    /**
                     * Pain Line Chart
                     */
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = dimensionResource(id = R.dimen.padding_large),
                                    start = dimensionResource(id = R.dimen.padding_medium),
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

                    /**
                     * Pain Line Chart
                     */
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = dimensionResource(id = R.dimen.padding_large),
                                    start = dimensionResource(id = R.dimen.padding_medium),
                                    bottom = dimensionResource(id = R.dimen.padding_small)
                                ),
                            text = stringResource(id = R.string.pain_pie_chart_title),
                            style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.onPrimary,
                            textAlign = TextAlign.Start
                        )

                        PieChart(state.pieChartData)
                    }
                }
            }
        }
    }
}