package com.applications.toms.mimetodoplanificado.ui.components.analytics

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.applications.toms.domain.LineChartEntity
import com.applications.toms.mimetodoplanificado.R
import com.applications.toms.mimetodoplanificado.ui.theme.DefaultAxisColor
import com.applications.toms.mimetodoplanificado.ui.theme.DefaultAxisLabelColor
import com.applications.toms.mimetodoplanificado.ui.utils.dpToPx

@Composable
fun LineChart(
    modifier: Modifier? = Modifier.padding(top = 16.dp, bottom = 16.dp),
    lineChartData: List<LineChartEntity>,
    verticalAxisValues: List<Float>,
    axisColor: Color = DefaultAxisColor,
    horizontalAxisLabelColor: Color = DefaultAxisLabelColor,
    horizontalAxisLabelFontSize: TextUnit = 16.sp,
    verticalAxisLabelColor: Color = DefaultAxisLabelColor,
    verticalAxisLabelFontSize: TextUnit = 16.sp,
    isShowVerticalAxis: Boolean = false,
    isShowHorizontalLines: Boolean = true,
    strokeWidth: Dp = 2.dp,
    lineColor: Color = Color.Black,
) {
    val strokeWidthPx = dpToPx(strokeWidth)
    val axisThicknessPx = dpToPx(dimensionResource(id = R.dimen.DefaultAxisThickness))

    Canvas(
        modifier = modifier!!.aspectRatio(1f)
    ) {

        val bottomAreaHeight = horizontalAxisLabelFontSize.toPx() + 8
        val leftAreaWidth =
            (verticalAxisValues[verticalAxisValues.size - 1].toString().length * verticalAxisLabelFontSize.toPx()
                .div(3)).toInt()

        val verticalAxisLength = (size.height - bottomAreaHeight)
        val horizontalAxisLength = size.width - leftAreaWidth

        val distanceBetweenVerticalAxisValues = (verticalAxisLength / (verticalAxisValues.size - 1))

        // Draw horizontal axis
        if (isShowHorizontalLines.not())
            drawRect(
                color = axisColor,
                topLeft = Offset(leftAreaWidth.toFloat(), verticalAxisLength),
                size = Size(horizontalAxisLength, axisThicknessPx)
            )

        // Draw vertical axis
        if (isShowVerticalAxis)
            drawRect(
                color = axisColor,
                topLeft = Offset(leftAreaWidth.toFloat(), 0.0f),
                size = Size(axisThicknessPx, verticalAxisLength)
            )

        // Draw vertical axis values & horizontal lines
        for (index in verticalAxisValues.indices) {

            val x = (leftAreaWidth / 2).toFloat()
            val y = verticalAxisLength - (distanceBetweenVerticalAxisValues).times(index)

            // Draw vertical axis value
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    verticalAxisValues[index].toInt().toString(),
                    x,
                    y + verticalAxisLabelFontSize.toPx() / 2,
                    Paint().apply {
                        textSize = verticalAxisLabelFontSize.toPx()
                        color = verticalAxisLabelColor.toArgb()
                        textAlign = Paint.Align.CENTER
                    }
                )
            }

            // Draw horizontal line
            if (isShowHorizontalLines)
                drawRect(
                    color = axisColor,
                    topLeft = Offset(leftAreaWidth.toFloat(), y),
                    size = Size(horizontalAxisLength, axisThicknessPx)
                )
        }

        // Draw lines and it's labels
        val barWidth =
            (drawContext.size.width - leftAreaWidth) / lineChartData.size

        val maxAxisValue = verticalAxisValues[verticalAxisValues.size - 1]

        var previousOffset: Offset? = null

        for (index in lineChartData.indices) {
            val entity = lineChartData[index]

            // Draw line
            val currentOffset = calculateOffset(
                entity.value,
                index,
                maxAxisValue,
                barWidth,
                leftAreaWidth,
                verticalAxisLength
            )

            val end = Offset(currentOffset.x + barWidth.div(2), currentOffset.y)

            drawCircle(
                color = lineColor,
                center = end,
                radius = strokeWidthPx.times(1.75f)
            )

            if (previousOffset != null) {
                val start = Offset(previousOffset.x + barWidth.div(2), previousOffset.y)
                drawLine(
                    start = start,
                    end = end,
                    color = lineColor.copy(alpha = 0.5f),
                    strokeWidth = strokeWidthPx
                )
            }

            previousOffset = currentOffset

            // Draw horizontal axis label
            if (lineChartData[index].label?.isNotEmpty() == true) {
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        lineChartData[index].label!!,
                        currentOffset.x + barWidth.div(2),
                        verticalAxisLength + bottomAreaHeight,
                        Paint().apply {
                            textSize = horizontalAxisLabelFontSize.toPx()
                            color = horizontalAxisLabelColor.toArgb()
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
            }
        }
    }
}

private fun calculateOffset(
    value: Float,
    index: Int,
    maxAxisValue: Float,
    barWidth: Float,
    leftAreaWidth: Int,
    verticalAxisLength: Float
): Offset {
    var x = barWidth * index
    x += leftAreaWidth

    val barHeightPercentage = (value / maxAxisValue)
    val barHeightInPixel = barHeightPercentage * verticalAxisLength
    val y = verticalAxisLength - barHeightInPixel

    return Offset(x, y)
}
