package com.applications.toms.mimetodoplanificado.ui.components

import android.view.ContextThemeWrapper
import android.widget.CalendarView
import android.widget.TimePicker
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.applications.toms.mimetodoplanificado.R
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalDate

val decimalFormat: NumberFormat = DecimalFormat("00")

@Composable
fun CustomCalendarView(onDateSelected: (LocalDate) -> Unit) {
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            CalendarView(ContextThemeWrapper(context, R.style.CalenderViewCustom))
        },
        update = { view ->
            view.setOnDateChangeListener { _, year, month, dayOfMonth ->
                onDateSelected(
                    LocalDate
                        .now()
                        .withMonth(month + 1)
                        .withYear(year)
                        .withDayOfMonth(dayOfMonth)
                )
            }
        }
    )
}

@Composable
fun CustomTimePicker(timeSet: String? = null,onTimeSelected: (String) -> Unit) {
    AndroidView(
        factory = { context ->
            TimePicker(
                ContextThemeWrapper(context, R.style.CalenderViewCustom)
            ).apply {
                setIs24HourView(true)
                if (timeSet != null){
                    hour = timeSet.split(":").first().toInt()
                    minute = timeSet.split(":")[1].toInt()
                } else {
                    minute = 0
                }
            }
        },
        modifier = Modifier.wrapContentSize(),
        update = { view ->
            view.setOnTimeChangedListener { _, hour, min ->
                onTimeSelected(
                    "${decimalFormat.format(hour)}:${decimalFormat.format(min)}"
                )
            }

        }
    )
}

