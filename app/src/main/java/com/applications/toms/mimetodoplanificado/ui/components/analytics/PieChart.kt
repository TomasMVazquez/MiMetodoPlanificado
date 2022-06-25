package com.applications.toms.mimetodoplanificado.ui.components.analytics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.applications.toms.domain.PieChartEntity
import com.applications.toms.mimetodoplanificado.R

@Composable
fun PieChart(
    data: List<PieChartEntity>,
    size: Dp = dimensionResource(id = R.dimen.pie_chart_size)
) {

    Row(modifier = Modifier
        .padding(dimensionResource(id = R.dimen.padding_large),)
        .fillMaxWidth()
        .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Sum of all the values
        val sumOfValues = data.sumOf { it.value.toLong() }

        // Calculate each proportion value
        val proportions = data.map { it.value * 100 / sumOfValues }

        // Convert each proportions to angle
        val sweepAngles = proportions.map {
            360 * it / 100
        }

        Canvas(
            modifier = Modifier.size(size = size)
        ) {

            var startAngle = -90f

            for (i in sweepAngles.indices) {
                drawArc(
                    color = Color(data[i].color),
                    startAngle = startAngle,
                    sweepAngle = sweepAngles[i],
                    useCenter = true
                )
                startAngle += sweepAngles[i]
            }

        }

        Column {
            for (i in data.indices) {
                DisplayLegend(color = Color(data[i].color), legend = data[i].label)
            }
        }

    }

}

@Composable
fun DisplayLegend(
    color: Color,
    legend: String
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_xxlarge)),
            thickness = dimensionResource(id = R.dimen.border_thickness),
            color = color
        )

        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.border_thickness)))

        Text(
            text = legend,
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.caption,
        )
    }
}