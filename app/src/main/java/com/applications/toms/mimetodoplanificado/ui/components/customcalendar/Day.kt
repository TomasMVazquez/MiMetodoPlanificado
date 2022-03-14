package com.applications.toms.mimetodoplanificado.ui.components.customcalendar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.applications.toms.domain.CalendarDay
import com.applications.toms.domain.enums.DaySelectedStatus
import com.applications.toms.mimetodoplanificado.ui.theme.VividRaspberry
import com.applications.toms.mimetodoplanificado.ui.utils.color
import com.applications.toms.mimetodoplanificado.ui.utils.isMarked

@Composable
fun Day(
    day: CalendarDay,
    isToday: Boolean
) {
    DayContainer(
        backgroundColor = day.status.color(MaterialTheme.colors),
    ) {
        DayStatusContainer(status = day.status) {
            Text(
                modifier = if (!isToday)
                    Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                else
                    Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colors.primaryVariant,
                            shape = CircleShape
                        )
                        .padding(2.dp),
                text = day.value,
                color = if (day.status.isMarked()) MaterialTheme.colors.primaryVariant
                else MaterialTheme.colors.onPrimary,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun DayStatusContainer(
    status: DaySelectedStatus,
    content: @Composable () -> Unit
) {
    if (status.isMarked()) {
        Box {
            val color = MaterialTheme.colors.secondary
            Circle(color = color)
            if (status == DaySelectedStatus.FirstDay) {
                SemiRect(
                    color = color,
                    lookingLeft = false
                )
            } else if (status == DaySelectedStatus.LastDay) {
                SemiRect(
                    color = color,
                    lookingLeft = true
                )
            }
            content()
        }
    } else {
        content()
    }
}
