package com.applications.toms.mimetodoplanificado.ui.screen.analytics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.applications.toms.domain.LineChartEntity
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.components.analytics.LineChart
import com.applications.toms.mimetodoplanificado.ui.components.painscale.CardPain
import com.applications.toms.mimetodoplanificado.ui.theme.LightBlack
import com.applications.toms.mimetodoplanificado.ui.theme.Purple
import com.applications.toms.mimetodoplanificado.ui.theme.VividRaspberry
import com.applications.toms.mimetodoplanificado.ui.utils.painscale.painScaleCards

@ExperimentalMaterialApi
@Composable
fun MyAnalytics(
    goBack: () -> Unit
) {

    AnalyticsContent(goBack = goBack)
}

@ExperimentalMaterialApi
@Composable
fun AnalyticsContent(
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

        LazyColumn {
            item {
                /**
                 * Pain Chart
                 */
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(id = R.dimen.padding_large),
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
                            onClickCard = {  }
                        )
                    }
                }
            }

            item {
                /**
                 * Pain Chart
                 */
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = dimensionResource(id = R.dimen.padding_medium),
                            start = dimensionResource(id = R.dimen.padding_large),
                            bottom = dimensionResource(id = R.dimen.padding_medium)
                        ),
                    text = stringResource(id = R.string.pain_line_chart_title),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onPrimary,
                    textAlign = TextAlign.Start
                )

                val lineChartData = ArrayList<LineChartEntity>()
                lineChartData.add(LineChartEntity(0f, "1"))
                lineChartData.add(LineChartEntity(2f, "2"))
                lineChartData.add(LineChartEntity(3f, "3"))
                lineChartData.add(LineChartEntity(4f, "4"))
                lineChartData.add(LineChartEntity(5f, "5"))
                lineChartData.add(LineChartEntity(4f, "6"))
                lineChartData.add(LineChartEntity(6f, "7"))
                lineChartData.add(LineChartEntity(7f, "8"))
                lineChartData.add(LineChartEntity(8f, "9"))
                lineChartData.add(LineChartEntity(4f, "10"))
                lineChartData.add(LineChartEntity(4f, "11"))
                lineChartData.add(LineChartEntity(4f, "12"))
                lineChartData.add(LineChartEntity(4f, "13"))
                lineChartData.add(LineChartEntity(4f, "14"))
                lineChartData.add(LineChartEntity(4f, "15"))
                lineChartData.add(LineChartEntity(4f, "16"))
                lineChartData.add(LineChartEntity(4f, "17"))
                lineChartData.add(LineChartEntity(4f, "18"))
                lineChartData.add(LineChartEntity(4f, "19"))
                lineChartData.add(LineChartEntity(4f, "20"))
                lineChartData.add(LineChartEntity(4f, "21"))
                lineChartData.add(LineChartEntity(4f, "22"))
                lineChartData.add(LineChartEntity(4f, "23"))
                lineChartData.add(LineChartEntity(4f, "24"))
                lineChartData.add(LineChartEntity(4f, "25"))
                lineChartData.add(LineChartEntity(4f, "26"))
                lineChartData.add(LineChartEntity(4f, "27"))
                lineChartData.add(LineChartEntity(4f, "28"))

                LineChart(
                    lineChartData = lineChartData,
                    verticalAxisValues = listOf(0f,1f,2f,3f,4f,5f,6f,7f,8f),
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